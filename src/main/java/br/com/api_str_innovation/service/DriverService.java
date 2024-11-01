package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.driver.DriverRequestDTO;
import br.com.api_str_innovation.dto.driver.DriverResponseDTO;
import br.com.api_str_innovation.entities.employee.DriverEntity;
import br.com.api_str_innovation.repository.DriverRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class DriverService {

    @Autowired
    private DriverRepository repository;

    public List<DriverResponseDTO> getAll() {
        List<DriverResponseDTO> drivers = repository
                .findAll()
                .stream()
                .map(DriverResponseDTO::new)
                .toList();
        return drivers;
    }

    @GetMapping(value = "/page")
    public Page<DriverResponseDTO> getPaged(Pageable pageable) {
        Page<DriverResponseDTO> drivers = repository
                .findAll(pageable)
                .map(DriverResponseDTO::new);
        return drivers;
    }

    public DriverEntity getById(UUID id) {
        DriverEntity driver = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found"));
        return driver;
    }

    public void post(@Valid DriverRequestDTO data) {
        DriverEntity driverData = new DriverEntity(data);
        repository.save(driverData);
    }

    public DriverResponseDTO put(@PathVariable UUID id, @RequestBody DriverRequestDTO data) {
        DriverEntity driver = this.getById(id);
        driver.setName(data.getName());
        driver.setLogin(data.getLogin());
        driver.setEmail(data.getEmail());
        driver.setPassword(data.getPassword()); // To adopt method to forget my password by email
        driver.setStatus(data.getStatus());
        repository.save(driver);
        return new DriverResponseDTO(driver);
    }

    public void inactivate(UUID id) {
        DriverEntity driverData = getById(id);
        driverData.setInactivatedDt(LocalDateTime.now());
        repository.save(driverData);
    }

}
