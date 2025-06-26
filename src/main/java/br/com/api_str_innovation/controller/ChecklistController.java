package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
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
    public ResponseEntity<Void> post(@PathVariable UUID vehicleId, @RequestBody ChecklistRequestDTO data) {
        this.service.post(vehicleId, data);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/get-by-vehicle-id/{vehicleId}")
    public ResponseEntity<ChecklistResponseDTO> getByVehicleId(@PathVariable UUID vehicleId) {
        ChecklistResponseDTO checklist = this.service.getByVehicleId(vehicleId);
        return ResponseEntity.ok(checklist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Deletado");
    }
}
