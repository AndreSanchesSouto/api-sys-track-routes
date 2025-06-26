package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.service.ClientService;
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
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping("/available")
    public ResponseEntity<List<ClientResponseDTO>> getAvailable(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAvailable(generalManagerId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count(generalManagerId));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClientResponseDTO>> getPaged(Pageable pageable, @RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable, generalManagerId));
    }

    @GetMapping(value = "/search/{attribute}")
    public ResponseEntity<Page<ClientResponseDTO>> searchByAttribute(
            Pageable pageable,
            @PathVariable String attribute,
            @RequestParam String value,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getSearched(pageable, attribute, value, generalManagerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<String> post(
            @Valid @RequestBody ClientRequestDTO data,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        this.service.post(data, generalManagerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> put(@PathVariable UUID id, @Valid @RequestBody ClientRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> patch(@PathVariable UUID id, @Valid @RequestBody ClientRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.patch(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
    }

}
