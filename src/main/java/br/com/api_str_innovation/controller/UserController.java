package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.user.UserRequestDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.dto.user.update.UserUpdateRequestDTO;
import br.com.api_str_innovation.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<UserResponseDTO>> getDrivers(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getDrivers(generalManagerId));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<UserResponseDTO>> getPaged(
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable, generalManagerId));
    }

    @GetMapping(value = "/search/name")
    public ResponseEntity<Page<UserResponseDTO>> getSearched(
            Pageable pageable,
            String name,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getSearched(pageable, name, generalManagerId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count(generalManagerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<String> post(
            @Valid @RequestBody UserRequestDTO data,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        this.service.post(data, generalManagerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> put(@PathVariable UUID id, @Valid @RequestBody UserRequestDTO data) {
        this.service.put(id, data);
        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }

    @PostMapping("/period-of-creation")
    ResponseEntity<List<Object[]>> periodOfCreation(@Valid @RequestBody PeriodTimeRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.periodOfCreation(data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patch(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequestDTO data) {
        this.service.patch(id, data);
        return ResponseEntity.status(HttpStatus.OK).body("Atualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        return ResponseEntity.status(HttpStatus.OK).body("Inativado");
    }

}