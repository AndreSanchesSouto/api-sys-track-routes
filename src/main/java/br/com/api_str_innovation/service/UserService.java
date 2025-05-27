package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.user.UserRequestDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.dto.user.update.UserUpdateRequestDTO;
import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.UserStatus;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.repository.UserRepository;
import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

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
        if (data.document() == null) {
            if (data.role().equals(Role.GENERAL_MANAGER)) {
                throw new UserException("O CNPJ é obrigatório para Gerente Geral!");
            }
            return;
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

    @GetMapping(value = "search/name")
    public Page<UserResponseDTO> getSearched(Pageable pageable, String name, UUID generalManagerId) {
        return repository
                .findSearchClients(pageable, name, generalManagerId)
                .map(UserResponseDTO::new);
    }

    public UserResponseDTO getById(UUID id) {
        UserEntity user = findById(id);
        return new UserResponseDTO(user);
    }

    public void postGeneralManager(@Valid UserRequestDTO data) {
        existsMailOrLoginOrDocument(data, null);
        validateDocumentByRole(data);

        UserEntity user = new UserEntity(data);
        repository.save(user);
    }

    public void post(@Valid UserRequestDTO data, UUID generalManagerId) {
        existsMailOrLoginOrDocument(data, null);
        validateDocumentByRole(data);

        UserEntity user = new UserEntity(data, generalManagerId);
        repository.save(user);
    }

    public List<Object[]> periodOfCreation(PeriodTimeRequestDTO periodTimeDTO) {
        return repository.periodTime(periodTimeDTO.from(), periodTimeDTO.to());
    }

    @Transactional
    public void patch(@PathVariable UUID id, @RequestBody UserUpdateRequestDTO data) {
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
    }

    @Transactional
    public void patchStatus(@PathVariable UUID id, @RequestBody UserStatus status) {
        UserEntity user = findById(id);
        user.setStatus(status.getStatus());
        repository.save(user);
    }

    @Transactional
    public void put(@PathVariable UUID id, @RequestBody UserRequestDTO data) {
        UserEntity user = findById(id);
        existsMailOrLoginOrDocument(data, user);
        validateDocumentByRole(data);

        user.setName(data.name());
        user.setEmail(data.email());
        user.setLogin(data.login());
        user.setDocument(data.document());
        user.setStatus(data.status().getStatus());
        repository.save(user);
    }

    @Transactional
    public void inactivate(UUID id) {
        UserEntity user = findById(id);

        if(user.getInactivatedDt() != null) {
            throw new UserException("Usuário já inativo");
        }

        user.setInactivatedDt(LocalDate.now());
        repository.save(user);
    }

}
