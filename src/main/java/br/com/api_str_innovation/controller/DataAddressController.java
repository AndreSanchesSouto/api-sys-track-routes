package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.data_address.DataAddressRequestDTO;
import br.com.api_str_innovation.dto.data_address.DataAddressResponseDTO;
import br.com.api_str_innovation.service.DataAddressService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class DataAddressController {

    @Autowired
    private DataAddressService service;

    @GetMapping
    public ResponseEntity<List<DataAddressResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<DataAddressResponseDTO>> getByClientId(@PathVariable UUID clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getByClientId(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAddressResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<DataAddressResponseDTO> post(
            @PathVariable UUID clientId,
            @RequestBody @Valid DataAddressRequestDTO data
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.post(clientId, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataAddressResponseDTO> put(
            @PathVariable UUID id,
          @RequestBody @Valid DataAddressRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }
}
