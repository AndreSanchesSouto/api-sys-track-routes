package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.ResponseDTO;
import br.com.api_str_innovation.dto.general_manager.GeneralManagerRequestDTO;
import br.com.api_str_innovation.dto.general_manager.GeneralManagerResponseDTO;
import br.com.api_str_innovation.entities.employee.GeneralManagerDomain;
import br.com.api_str_innovation.service.GeneralManagerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/general-manager")
public class GeneralManagerController {

    @Autowired
    private GeneralManagerService service;

    @GetMapping
    public ResponseEntity<List<GeneralManagerResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<GeneralManagerResponseDTO>> getPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralManagerDomain> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@Valid @RequestBody GeneralManagerRequestDTO data) {
        this.service.post(data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralManagerResponseDTO> put(@PathVariable UUID id, @RequestBody GeneralManagerRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        // There is an error when the message show "Inativado com sucesso", but the driver wasn`t inactivated.
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Inativado com sucesso"));
    }
}
