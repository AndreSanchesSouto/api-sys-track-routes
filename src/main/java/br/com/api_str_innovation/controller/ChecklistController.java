package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {

    @Autowired
    private ChecklistService service;

    @PostMapping("/{vehicleId}")
    public ResponseEntity<String> post(@PathVariable UUID vehicleId, @RequestBody ChecklistRequestDTO data) {
        this.service.post(vehicleId, data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado com sucesso");
    }

}
