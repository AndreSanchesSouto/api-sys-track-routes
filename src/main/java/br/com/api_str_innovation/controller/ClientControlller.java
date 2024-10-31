package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.ResponseDTO;
import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientControlller {

    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClientResponseDTO>> getPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@PathVariable ClientRequestDTO data) {
        this.service.post(data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> put(@PathVariable UUID id, @RequestBody ClientRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<ResponseDTO> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Deletado com sucesso"));
    }

}
