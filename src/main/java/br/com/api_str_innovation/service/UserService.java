package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.employee.UserRequestDTO;
import br.com.api_str_innovation.dto.employee.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.entities.user.Status;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.exceptions.UserException;
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

    private UserEntity findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
    }

    private void existsMailOrLogin(UserRequestDTO data) {
        if (repository.findByEmail(data.email()).isPresent() ) {
            throw new UserException(String.format("O email %s já está em uso.", data.email()));
        }

        if(repository.findByLogin(data.login()) != null ){
            throw new UserException(String.format("O login %s já está em uso.", data.login()));
        }
    }

    public List<UserResponseDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(UserResponseDTO::create)
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
                .map(UserResponseDTO::create);
    }

    public UserResponseDTO getById(UUID id) {
        UserEntity user = findById(id);
        return UserResponseDTO.create(user);
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
        UserEntity userData = findById(id);

        repository.update(
                id,
                data.name() == null ? userData.getName() : data.name(),
                data.email() == null ? userData.getEmail() : data.email(),
                data.login() == null ? userData.getLogin() : data.login(),
                data.status() == null ? Status.valueOf(userData.getStatus()) : data.status()
        );
    }

    @Transactional
    public void put(@PathVariable UUID id, @RequestBody UserRequestDTO data) {
        existsMailOrLogin(data);
        UserEntity userData = findById(id);

        repository.update(
                id,
                data.name() == null ? userData.getName() : data.name(),
                data.email() == null ? userData.getEmail() : data.email(),
                data.login() == null ? userData.getLogin() : data.login(),
                data.status() == null ? Status.valueOf(userData.getStatus()) : data.status()
        );
    }

    @Transactional
    public void inactivate(UUID id) {
        UserEntity user = findById(id);

        if (user.getInactivatedDt() != null) {
            throw new UserException("Cliente já inativo");
        }

        repository.inactivateUser(id, LocalDate.now());

    }

}
