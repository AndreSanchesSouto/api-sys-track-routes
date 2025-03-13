package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.user.UserRequestDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.Status;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.exceptions.VehicleException;
import br.com.api_str_innovation.repository.UserRepository;
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

    private void existsMailOrLogin(UserRequestDTO data) {
        if (repository.findByEmail(data.email()).isPresent() ) {
            throw new UserException(String.format("O email %s já está em uso.", data.email()));
        }

        if(repository.findByLogin(data.login()).isPresent() ){
            throw new UserException(String.format("O login %s já está em uso.", data.login()));
        }
    }

    public List<UserResponseDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public List<UserResponseDTO> getDrivers() {
        return repository
                .findUsersActivated(Role.DRIVER.getRole(), Status.ACTIVE.getStatus())
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public Integer count() {
        return repository
                .findActiveUsers()
                .toArray()
                .length;
    }

    @GetMapping(value = "/page")
    public Page<UserResponseDTO> getPaged(Pageable pageable) {
        return repository
                .findActiveUsers(pageable)
                .map(UserResponseDTO::new);
    }

    public UserResponseDTO getById(UUID id) {
        UserEntity user = findById(id);
        return new UserResponseDTO(user);
    }

    public void post(@Valid UserRequestDTO data) {
        existsMailOrLogin(data);
        UserEntity user = new UserEntity(data);
        repository.save(user);
    }

    public List<Object[]> periodOfCreation(PeriodTimeRequestDTO periodTimeDTO) {
        return repository.periodTime(periodTimeDTO.from(), periodTimeDTO.to());
    }

    @Transactional
    public void patch(@PathVariable UUID id, @RequestBody UserRequestDTO data) {
        existsMailOrLogin(data);
        UserEntity user = findById(id);

        user.setName(data.name());
        user.setEmail(data.email());
        user.setLogin(data.login());
        user.setStatus(data.status().getStatus());
        repository.save(user);
    }

    @Transactional
    public void put(@PathVariable UUID id, @RequestBody UserRequestDTO data) {
        existsMailOrLogin(data);
        UserEntity user = findById(id);

        user.setName(data.name());
        user.setEmail(data.email());
        user.setLogin(data.login());
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
