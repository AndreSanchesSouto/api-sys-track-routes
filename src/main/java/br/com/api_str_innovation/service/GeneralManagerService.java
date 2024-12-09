package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.employee.ResponseDTO;
import br.com.api_str_innovation.dto.employee.general_manager.GeneralManagerRequestDTO;
import br.com.api_str_innovation.dto.employee.general_manager.GeneralManagerResponseDTO;
import br.com.api_str_innovation.entities.employee.GeneralManagerEntity;
import br.com.api_str_innovation.repository.GeneralManagerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "General Manager not found"));
        return generalManager;
    }

    public ResponseEntity<ResponseDTO> post(@Valid GeneralManagerRequestDTO data) {
        if (repository.findByLogin(data.getLogin()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("Usuário já cadastrado"));
        }
        GeneralManagerEntity generalManager = new GeneralManagerEntity(data.getName(), data.getEmail(), data.getLogin(), data.getPassword(), data.getRole());
        repository.save(generalManager);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Criado com sucesso"));

    }

    public GeneralManagerResponseDTO put(UUID id, GeneralManagerRequestDTO data) {
        GeneralManagerEntity generalManager = this.getById(id);
        generalManager.setName(data.getName());
        generalManager.setEmail(data.getEmail());
        generalManager.setLogin(data.getLogin());
        generalManager.setPassword(data.getPassword());
        repository.save(generalManager);
        return new GeneralManagerResponseDTO(generalManager);

    }

    public void inactivate(UUID id) {
        GeneralManagerEntity generalManager = getById(id);
        generalManager.setInactivatedDt(LocalDate.now());
        repository.save(generalManager);
    }

}

