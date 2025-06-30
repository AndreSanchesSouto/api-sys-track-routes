package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.dashboard.DashboardVehiclesDTO;
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
    public ResponseEntity<List<VehicleEntity>> getAllByGeneralManagerId(
            @RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllByGeneralManagerId(generalManagerId));
    }

    @GetMapping("/available")
    public ResponseEntity<List<VehicleResponseDTO>> getAvailableVehicles(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findAvailableVehicles(generalManagerId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count(generalManagerId));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<VehicleResponseDTO>> getPaged(
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable, generalManagerId));
    }

    @GetMapping("/vehicles-status")
    public ResponseEntity<DashboardVehiclesDTO> getVehicleStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return this.service.getVehiclesStatus(generalManagerId);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Page<VehicleResponseDTO>> getVehiclesByStatus(
            @RequestParam String status,
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getByStatus(status, pageable, generalManagerId));
    }

    @GetMapping(value = "/search/{attribute}")
    public ResponseEntity<Page<VehicleResponseDTO>> searchByAttribute(
            Pageable pageable,
            @PathVariable String attribute,
            @RequestParam String value,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getSearched(pageable, attribute, value, generalManagerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<String> post(
            @RequestBody VehicleRequestDTO data,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        this.service.post(data, generalManagerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> put(@PathVariable UUID id, @RequestBody VehicleRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> patch(@PathVariable UUID id, @RequestBody VehicleRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.patch(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        return ResponseEntity.status(HttpStatus.OK).body("Inativado com sucesso");
    }

}