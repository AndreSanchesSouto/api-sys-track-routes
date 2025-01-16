package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<VehicleResponseDTO>> getPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleEntity> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody VehicleRequestDTO data) {
        this.service.post(data);
        // There is an error when the message show "Criado com sucesso", but the driver wasn`t created.
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado com sucesso");
    }

    @PostMapping("/{vehicleId}/checklist")
    public ResponseEntity<String> post(@PathVariable UUID vehicleId,
                                                @RequestBody ChecklistRequestDTO data) {
        this.service.createChecklist(vehicleId, data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> put(@PathVariable UUID id, @RequestBody VehicleRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    // inactivate driver
    @DeleteMapping("/{id}")
    public ResponseEntity<String> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        // There is an error when the message show "Inativado com sucesso", but the driver wasn`t inactivated.
        return ResponseEntity.status(HttpStatus.OK).body("Inativado com sucesso");
    }

}