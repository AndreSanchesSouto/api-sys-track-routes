package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.employee.ResponseDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {

    @Autowired
    private ChecklistService service;

    @GetMapping
    public ResponseEntity<List<ChecklistResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ChecklistResponseDTO>> getPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistEntity> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody ChecklistRequestDTO data) {
        this.service.post(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> put(@PathVariable UUID id, @RequestBody ChecklistRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

}
