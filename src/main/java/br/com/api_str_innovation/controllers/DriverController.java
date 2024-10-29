package br.com.api_str_innovation.controllers;

import br.com.api_str_innovation.dtos.ResponseDTO;
import br.com.api_str_innovation.dtos.driver.DriverRequestDTO;
import br.com.api_str_innovation.dtos.driver.DriverResponseDTO;
import br.com.api_str_innovation.entities.employee.DriverDomain;
import br.com.api_str_innovation.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService service;

    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDomain> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<DriverResponseDTO>> getPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@Valid @RequestBody DriverRequestDTO data) {
        this.service.post(data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Criado com sucesso"));
    }
     // inactivate driver
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        // There is an error when the message show "Inativado com sucesso", but the driver wasn`t inactivated.
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Inativado com sucesso"));
    }

}
