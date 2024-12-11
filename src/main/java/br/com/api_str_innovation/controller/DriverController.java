package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.employee.ResponseDTO;
import br.com.api_str_innovation.dto.employee.driver.DriverRequestDTO;
import br.com.api_str_innovation.dto.employee.driver.DriverResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.entities.employee.DriverEntity;
import br.com.api_str_innovation.service.DriverService;
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

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<DriverResponseDTO>> getPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverEntity> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody DriverRequestDTO data) {
        System.out.print(data.toString());
        this.service.post(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Criado com sucesso"));
    }

    @PostMapping("/period-of-creation")
    ResponseEntity<List<Object[]>> periodOfCreation(@RequestBody PeriodTimeRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.periodOfCreation(data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> patch(@PathVariable UUID id, @RequestBody DriverRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.patch(id, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> put(@PathVariable UUID id, @RequestBody DriverRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        // There is an error when the message show "Inativado com sucesso", but the driver wasn`t inactivated.
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Inativado com sucesso"));
    }

}