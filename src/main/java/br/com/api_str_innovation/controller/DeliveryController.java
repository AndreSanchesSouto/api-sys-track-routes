package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.delivery.DeliveryGenericResponseDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryProductsResponseDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryRequestDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryResponseDTO;
import br.com.api_str_innovation.dto.delivery.location.DeliveryLocationDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.service.DeliveryService;
import jakarta.transaction.Transactional;
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
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService service;

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> post(
            @Valid @RequestBody DeliveryRequestDTO data,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.post(data, generalManagerId));
    }

    @GetMapping("/{id}/current-driver-location")
    public ResponseEntity<DeliveryLocationDTO> getDriverLocation(@PathVariable UUID id) {
        return this.service.getDriverLocation(id);
    }

    @Transactional
    @PatchMapping("/{id}/send-current-location")
    public ResponseEntity sendCurrentLocation(
            @PathVariable UUID id,
            @Valid @RequestBody DeliveryLocationDTO data
    ) {
        return this.service.sendCurrentLocation(data, id);
    }

    @GetMapping("/my-deliveries/page")
    public ResponseEntity<Page<DeliveryGenericResponseDTO>> getDeliveryByDriverId(
            @RequestHeader("general-manager-id") UUID generalManagerId,
            @RequestHeader("user-id") UUID driverId,
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getDeliveryByDriverId(generalManagerId, driverId, pageable));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count(generalManagerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryProductsResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<DeliveryGenericResponseDTO>> getPaged(
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable, generalManagerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryProductsResponseDTO> put(@PathVariable UUID id, @Valid @RequestBody DeliveryRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }
}
