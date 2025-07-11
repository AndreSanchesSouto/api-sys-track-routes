package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.dashboard.DashboardDriversDTO;
import br.com.api_str_innovation.dto.user.report.PeriodCreationResponseDTO;
import br.com.api_str_innovation.dto.user.UserChangePasswordDTO;
import br.com.api_str_innovation.dto.user.UserRequestDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.dto.user.update.UserUpdateRequestDTO;
import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.UserStatus;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.infrastructure.security.Encrypter;
import br.com.api_str_innovation.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Lazy
    @Autowired
    private DeliveryService deliveryService;

    public UserEntity findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        );
    }

    private void existsMailOrLoginOrDocument(UserRequestDTO data, @Nullable UserEntity existUser) {
        Optional<UserEntity> emailExists = repository.findByEmail(data.email());
        if (emailExists.isPresent() && (existUser == null || !emailExists.get().getId().equals(existUser.getId()))) {
            throw new UserException(String.format("O email %s já está em uso.", data.email()));
        }

        Optional<UserEntity> loginExists = repository.findByLogin(data.login());
        if (loginExists.isPresent() && (existUser == null || !loginExists.get().getId().equals(existUser.getId()))) {
            throw new UserException(String.format("O login %s já está em uso.", data.login()));
        }

        if (data.document() != null) {
            Optional<UserEntity> documentExists = repository.findByDocument(data.document());
            if (documentExists.isPresent() && (existUser == null || !documentExists.get().getId().equals(existUser.getId()))) {
                throw new UserException(String.format("O documento %s já está em uso.", data.document()));
            }
        }
    }

    private void validateDocumentByRole(UserRequestDTO data) {
        if (data.document() == null && data.role().equals(Role.GENERAL_MANAGER)) {
            throw new UserException("O CNPJ é obrigatório para Gerente Geral!");
        }

        String document = data.document().replaceAll("\\D", "");
        
        if (data.role().equals(Role.GENERAL_MANAGER) && document.length() != 14) {
            throw new UserException("O CNPJ deve ser preenchido corretamente!");
        }

        if ((data.role().equals(Role.DRIVER) || data.role().equals(Role.SHIPPING_MANAGER)) 
            && document.length() != 11) {
            throw new UserException("O CPF deve ser preenchido corretamente!");
        }
    }

    public List<UserResponseDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public List<UserResponseDTO> getDrivers(UUID generalManagerId) {
        return repository
                .findUsersActivated(Role.DRIVER.getRole(), UserStatus.ACTIVE.getStatus(), generalManagerId)
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public Integer count(UUID generalManagerId) {
        return repository
                .findActiveUsers(generalManagerId)
                .toArray()
                .length;
    }

    @GetMapping(value = "/page")
    public Page<UserResponseDTO> getPaged(Pageable pageable, UUID generalManagerId) {
        return repository
                .findActiveUsers(pageable, generalManagerId)
                .map(UserResponseDTO::new);
    }

    public ResponseEntity<DashboardDriversDTO> getDriversStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        List<UserEntity> driverEntities = this.getAllDriversByGeneralManagerId(generalManagerId);
        int active = 0;
        int unavailable = 0;
        int inactive = 0;
        for(UserEntity driver : driverEntities) {
            switch(UserStatus.valueOf(driver.getStatus().toUpperCase())) {
                case ACTIVE -> active++;
                case UNAVAILABLE -> unavailable++;
                case INACTIVE -> inactive++;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new DashboardDriversDTO(active, unavailable, inactive));
    }


    public Page<UserResponseDTO> getByStatus(String status, Pageable pageable, UUID generalManagerId) {
        return repository
                .findByStatusAndGeneralManagerId(status.toLowerCase(), generalManagerId, pageable)
                .map(UserResponseDTO::new);
    }

    public Page<UserResponseDTO> getSearched(Pageable pageable, String attribute, String search, UUID generalManagerId) {
        return switch (attribute) {
            case "name" -> repository
                    .findSearchClientsByName(pageable, search, generalManagerId)
                    .map(UserResponseDTO::new);
            case "email" -> repository
                    .findSearchClientsByEmail(pageable, search, generalManagerId)
                    .map(UserResponseDTO::new);
            case "login" -> repository
                    .findSearchClientsByLogin(pageable, search, generalManagerId)
                    .map(UserResponseDTO::new);
            default -> throw new UserException("Parâmetro não aceito para a pesquisa");
        };

    }

    public UserResponseDTO getById(UUID id) {
        UserEntity user = findById(id);
        return new UserResponseDTO(user);
    }

    public ResponseEntity<Void> postGeneralManager(@Valid UserRequestDTO data) {
        this.validatePasswordAndConfirmation(data.password(), data.confirmPassword());

        existsMailOrLoginOrDocument(data, null);
        validateDocumentByRole(data);

        UserEntity user = new UserEntity(data);
        repository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void validatePasswordAndConfirmation(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) throw new UserException("As senhas não coincidem.");
    }

    public void post(@Valid UserRequestDTO data, UUID generalManagerId) {
        this.validatePasswordAndConfirmation(data.password(), data.confirmPassword());
        existsMailOrLoginOrDocument(data, null);
        if (data.document() != null) validateDocumentByRole(data);

        UserEntity user = new UserEntity(data, generalManagerId);
        user.setImageUrl(uploadImage(data.image()));
        repository.save(user);
    }

    private String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        try {
            File file = this.convertMultipartToFile(multipartFile);
            amazonS3.putObject(bucketName, fileName, file);
            file.delete();
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            System.out.println("ERROOOOOOOOOOOO " + e);
        }
        return null;
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convertFile;
    }

    public PeriodCreationResponseDTO periodOfCreation(PeriodTimeRequestDTO periodTimeDTO, UUID generalManagerId) {
        List<Object[]> periodResults = repository.periodTime(
                periodTimeDTO.from(),
                periodTimeDTO.to(),
                generalManagerId
        );

        List<UserEntity> users = repository.findUsersByPeriod(
                periodTimeDTO.from(),
                periodTimeDTO.to(),
                generalManagerId
        );

        List<PeriodCreationResponseDTO.PeriodReportDTO> periodData = periodResults.stream()
                .map(row -> new PeriodCreationResponseDTO.PeriodReportDTO(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).intValue(),
                        ((Number) row[2]).longValue()
                ))
                .toList();

        List<PeriodCreationResponseDTO.UserDetailDTO> userDetails = users.stream()
                .map(user -> new PeriodCreationResponseDTO.UserDetailDTO(
                        user.getName(),
                        user.getLogin(),
                        user.getRole(),
                        user.getCreatedDt().getYear(),
                        user.getCreatedDt().getMonthValue()
                ))
                .toList();

        return new PeriodCreationResponseDTO(periodData, userDetails);
    }

    public UserEntity getUserAtMoment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        return this.getByLogin(authenticatedUsername);
    }

    public ResponseEntity<Void> changeUserPassword(UUID id, UserChangePasswordDTO data) {
        if(!data.newEmployeePassword().equals(data.newEmployeePasswordConfirmation())) throw new UserException("As senhas não são correspondentes");


        UserEntity targetUser = this.findById(id);
        UserEntity actualUser = this.getUserAtMoment();

        return actualUser.getRole().equals(Role.GENERAL_MANAGER.getRole()) ?
                generalManagerChangePassword(data, targetUser, actualUser) :
                employeeChangePassword(data, targetUser, actualUser);


    }

    private ResponseEntity<Void> generalManagerChangePassword(UserChangePasswordDTO data, UserEntity target, UserEntity actualUser) {
        String hashPassword = Encrypter.encrypt(data.userPassword());
        if(repository.authIdentity(actualUser.getLogin(), hashPassword).isEmpty()){
            throw new UserException("Sua senha está incorreta");
        }

        this.patchPassword(target, Encrypter.encrypt(data.newEmployeePassword()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<Void> employeeChangePassword(UserChangePasswordDTO data, UserEntity target, UserEntity actualUser) {
        if (!actualUser.getRole().equals(Role.GENERAL_MANAGER.toString()) && !actualUser.getId().equals(target.getId())) {
            throw new UserException("Você não tem permissão para alterar a senha de outro usuário");
        }

        String hashPassword = Encrypter.encrypt(data.userPassword());
        if(repository.authIdentity(actualUser.getLogin(), hashPassword).isEmpty()){
            throw new UserException("Sua senha está incorreta");
        }

        this.patchPassword(target, Encrypter.encrypt(data.newEmployeePassword()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public UserEntity getByLogin(String login) {
        return this.repository.findByLogin(login).orElseThrow(() -> new UserException("Login não encontrado"));
    }

    @Transactional
    public void patchPassword(UserEntity user, String password) {
        user.setPassword(password);
        this.repository.save(user);
    }

    @Transactional
    public ResponseEntity<Void> patch(@PathVariable UUID id, @RequestBody UserUpdateRequestDTO data) {
        UserEntity user = findById(id);

        Optional<UserEntity> emailExists = repository.findByEmail(data.email());
        if (emailExists.isPresent() && !emailExists.get().getId().equals(user.getId())) {
            throw new UserException(String.format("O email %s já está em uso.", data.email()));
        }

        Optional<UserEntity> loginExists = repository.findByLogin(data.login());
        if (loginExists.isPresent() && !loginExists.get().getId().equals(user.getId())) {
            throw new UserException(String.format("O login %s já está em uso.", data.login()));
        }

        user.setName(data.name());
        user.setEmail(data.email());
        user.setLogin(data.login());
        user.setStatus(data.status().getStatus());
        repository.save(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Transactional
    public void patchStatus(@PathVariable UUID id, @RequestBody UserStatus status) {
        UserEntity user = findById(id);
        user.setStatus(status.getStatus());
        repository.save(user);
    }

    @Transactional
    public void inactivate(UUID id) {
        UserEntity user = findById(id);

        if(user.getInactivatedDt() != null) {
            throw new UserException("Usuário já inativo");
        }
        if(deliveryService.findActiveByUserId(id)!=null) {
            throw new UserException("Funcionário com entrega pendente");
        }
        user.setInactivatedDt(LocalDate.now());
        repository.save(user);
    }

    public List<UserEntity> getAllDriversByGeneralManagerId(UUID generalManagerId) {
        return this.repository.getAllDriversByGeneralManagerId(generalManagerId);
    }

    public List<UserEntity> getAllEmployeesByGeneralManagerId(UUID generalManagerId) {
        return this.repository.getAllEmployeesByGeneralManagerId(generalManagerId);
    }
}
