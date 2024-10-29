package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dtos.driver.DriverRequestDTO;
import br.com.api_str_innovation.dtos.driver.DriverResponseDTO;
import br.com.api_str_innovation.entities.employee.DriverDomain;
import br.com.api_str_innovation.repository.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
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

    public DriverDomain getById(UUID id) {
        DriverDomain driver = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Driver not found"));
        return driver;
    }

    @GetMapping(value = "/page")
    public Page<DriverResponseDTO> getPaged(Pageable pageable) {
        Page<DriverResponseDTO> drivers = repository
                .findAll(pageable)
                .map(DriverResponseDTO::new);
        return drivers;
    }

    public void post(@RequestBody DriverRequestDTO data) {
        System.out.println(data);
        DriverDomain driverData = new DriverDomain(data);
        repository.save(driverData);
    }

    // Inactivate method
    public void inactivate(UUID id) {
        DriverDomain driverData = getById(id);
        driverData.setInactivationDt(new Date());
        repository.save(driverData);
    }
}
