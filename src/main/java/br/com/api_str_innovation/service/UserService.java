package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.employee.UserRequestDTO;
import br.com.api_str_innovation.dto.employee.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.entities.employee.UserEntity;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.repository.UserRepository;
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

    public List<UserResponseDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(UserResponseDTO::create)
                .toList();
    }

    public Integer count() {
        return repository
                .findActiveDrivers()
                .toArray()
                .length;
    }

    @GetMapping(value = "/page")
    public Page<UserResponseDTO> getPaged(Pageable pageable) {
        return repository
                .findActiveDrivers(pageable)
                .map(UserResponseDTO::create);
    }

    public UserEntity getById(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found"));
    }

    public void post(@Valid UserRequestDTO data) {
        if (repository.findByEmail(data.email()).isPresent() ) {
            throw new UserException(String.format("O email %s já está em uso.", data.email()));
        }

        if(repository.findByLogin(data.login()) != null ){
            throw new UserException(String.format("O login %s já está em uso.", data.login()));
        }

        UserEntity user = new UserEntity(data);
        repository.save(user);
    }

    public List<Object[]> periodOfCreation(PeriodTimeRequestDTO periodTimeDTO) {
        return repository.periodTime(periodTimeDTO.from(), periodTimeDTO.to());
    }

    public UserResponseDTO patch(@PathVariable UUID id, @RequestBody UserRequestDTO data) {
        UserEntity driver = this.getById(id);
        driver.setName(data.name());
        driver.setLogin(data.login());
        driver.setEmail(data.email());
        driver.setStatus(data.status());
        repository.save(driver);
        return UserResponseDTO.create(driver);
    }

    public UserResponseDTO put(@PathVariable UUID id, @RequestBody UserRequestDTO data) {
        UserEntity driver = this.getById(id);
        driver.setName(data.name());
        driver.setLogin(data.login());
        driver.setEmail(data.email());
        driver.setPassword(data.password()); // To adopt method to for my password by email
        driver.setStatus(data.status());
        repository.save(driver);
        return UserResponseDTO.create(driver);
    }

    public void inactivate(UUID id) {
        UserEntity driverData = getById(id);
        driverData.setInactivatedDt(LocalDate.now());
        repository.save(driverData);
    }

}
