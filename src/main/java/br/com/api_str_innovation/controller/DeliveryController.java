package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.dashboard.DashboardDeliveryDTO;
import br.com.api_str_innovation.dto.delivery.*;
import br.com.api_str_innovation.dto.delivery.location.DeliveryLocationDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.projections.DeliveryTableProjection;
import br.com.api_str_innovation.service.DeliveryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService service;

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

    @PatchMapping("/{id}/send-current-location")
    public ResponseEntity<Void> sendCurrentLocation(@PathVariable UUID id, @Valid @RequestBody DeliveryLocationDTO location) {
        return this.service.sendCurrentLocation(id, location);
    }

    @GetMapping("/my-deliveries/page")
    public ResponseEntity<Page<DeliveryGenericResponseDTO>> getDeliveryByDriverId(
            @RequestHeader("general-manager-id") UUID generalManagerId,
            @RequestHeader("user-id") UUID driverId,
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getDeliveryByDriverId(generalManagerId, driverId, pageable));
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Page<DeliveryTableProjection>> getDriversByStatus(
            @RequestParam String status,
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getByStatus(status, pageable, generalManagerId));
    }

    @PostMapping(value = "/report/period")
    public ResponseEntity<List<DeliveryReportDTO>> getAllDeliveriesByPeriod(
            @Valid @RequestBody PeriodTimeRequestDTO data,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllDeliveriesByPeriod(data, generalManagerId));
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

    @GetMapping("/delivery-status")
    public ResponseEntity<DashboardDeliveryDTO> getDeliveryStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return this.service.getDeliveryStatus(generalManagerId);
    }

    @GetMapping(value = "/search/{attribute}")
    public ResponseEntity<Page<DeliveryGenericResponseDTO>> searchByAttribute(
            Pageable pageable,
            @PathVariable String attribute,
            @RequestParam String value,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getSearched(pageable, attribute, value, generalManagerId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DeliveryProductsResponseDTO> patch(@PathVariable UUID id, @Valid @RequestBody DeliveryRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.patch(id, data));
    }

    @PatchMapping("/{id}/register-confirm")
    public ResponseEntity<Void> registerConfirm(@PathVariable UUID id) {
        return this.service.registerConfirm(id);
    }

    @PatchMapping("/start-delivery/{id}")
    public ResponseEntity<Void> patchStartDelivery(@PathVariable UUID id) {
        return this.service.patchStartDelivery(id);
    }

    @PatchMapping("{id}/inactive")
    public ResponseEntity<Void> inactiveDelivery(@PathVariable UUID id) {
        return this.service.inactiveDelivery(id);
    }
}
