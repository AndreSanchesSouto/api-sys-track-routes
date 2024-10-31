package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.EmployeeRequest;
import br.com.api_str_innovation.dto.general_manager.EmployeeResponseDTO;
import br.com.api_str_innovation.entities.employee.GeneralManagerEntity;
import br.com.api_str_innovation.repository.GeneralManagerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GeneralManagerService {

    @Autowired
    private GeneralManagerRepository repository;

    public List<EmployeeResponseDTO> getAll() {
        List<EmployeeResponseDTO> generalManagers = repository
                .findAll()
                .stream()
                .map(EmployeeResponseDTO::new)
                .toList();
        return generalManagers;
    }

    @GetMapping(value = "/page")
    public Page<EmployeeResponseDTO> getPaged(Pageable pageable) {
        Page<EmployeeResponseDTO> generalManagers = repository
                .findAll(pageable)
                .map(EmployeeResponseDTO::new);
        return generalManagers;
    }

    public GeneralManagerEntity getById(UUID id) {
        GeneralManagerEntity generalManager = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "General Manager not found"));
        return generalManager;
    }

    public void post(@Valid EmployeeRequest data) {
        System.out.println(data);
        GeneralManagerEntity generalManagerData = new GeneralManagerEntity(data);
        repository.save(generalManagerData);
    }

    public EmployeeResponseDTO put(UUID id, EmployeeRequest data) {
        GeneralManagerEntity generalManager = this.getById(id);
        generalManager.setName(data.getName());
        generalManager.setEmail(data.getEmail());
        generalManager.setLogin(data.getLogin());
        generalManager.setPassword(data.getPassword());
        repository.save(generalManager);
        return new EmployeeResponseDTO(generalManager);

    }

    public void inactivate(UUID id) {
        GeneralManagerEntity generalManager = getById(id);
        generalManager.setInactivationDt(new Date());
        repository.save(generalManager);
    }
}

