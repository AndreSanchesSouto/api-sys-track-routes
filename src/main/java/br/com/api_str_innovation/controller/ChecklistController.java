package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.checklist.checklist_log.ChecklistLogRequestDTO;
import br.com.api_str_innovation.dto.employee.UserResponseDTO;
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

    @GetMapping(value = "/{id}/page")
    public ResponseEntity<Page<ChecklistResponseDTO>> getPaged(Pageable pageable,
                                                               @PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistEntity> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping("/count-active")
    public ResponseEntity<List<Object[]>> countChecklistStatusVehicleActive(@RequestBody ChecklistLogRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.countChecklistStatusVehicleActive(data));
    }

    @PostMapping("/count-inactive")
    public ResponseEntity<List<Object[]>> countChecklistStatusVehicleInactive(@RequestBody ChecklistLogRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.countChecklistStatusVehicleInactive(data));
    }

    @PostMapping("/count-km/{vehicleId}")
    public ResponseEntity<List<Object[]>> countKmDriven(@PathVariable UUID vehicleId, @RequestBody ChecklistLogRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.countKmDriven(vehicleId, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> put(@PathVariable UUID id, @RequestBody ChecklistRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
    }

}
