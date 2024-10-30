package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.general_manager.GeneralManagerRequestDTO;
import br.com.api_str_innovation.dto.general_manager.GeneralManagerResponseDTO;
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

    public List<GeneralManagerResponseDTO> getAll() {
        List<GeneralManagerResponseDTO> generalManagers = repository
                .findAll()
                .stream()
                .map(GeneralManagerResponseDTO::new)
                .toList();
        return generalManagers;
    }

    @GetMapping(value = "/page")
    public Page<GeneralManagerResponseDTO> getPaged(Pageable pageable) {
        Page<GeneralManagerResponseDTO> generalManagers = repository
                .findAll(pageable)
                .map(GeneralManagerResponseDTO::new);
        return generalManagers;
    }

    public GeneralManagerEntity getById(UUID id) {
        GeneralManagerEntity generalManager = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "General Manager not found"));
        return generalManager;
    }

    public void post(@Valid GeneralManagerRequestDTO data) {
        System.out.println(data);
        GeneralManagerEntity generalManagerData = new GeneralManagerEntity(data);
        repository.save(generalManagerData);
    }

    public GeneralManagerResponseDTO put(UUID id, GeneralManagerRequestDTO data) {
        GeneralManagerEntity generalManager = this.getById(id);
        generalManager.setName(data.name());
        generalManager.setEmail(data.email());
        generalManager.setLogin(data.login());
        generalManager.setPassword(data.password());
        repository.save(generalManager);
        return new GeneralManagerResponseDTO(generalManager);

    }

    public void inactivate(UUID id) {
        GeneralManagerEntity generalManager = getById(id);
        generalManager.setInactivationDt(new Date());
        repository.save(generalManager);
    }
}

