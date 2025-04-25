package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.delivery.DeliveryGenericResponseDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryProductsResponseDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryRequestDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryResponseDTO;
import br.com.api_str_innovation.service.DeliveryService;
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
