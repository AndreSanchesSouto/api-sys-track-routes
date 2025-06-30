package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.delivery.LogsUserDeliveryDTO;
import br.com.api_str_innovation.service.LogsUserDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/logs/delivery")
@CrossOrigin(origins = "*")
public class LogsUserDeliveryController {
    
    @Autowired
    private LogsUserDeliveryService logsUserDeliveryService;

    @GetMapping("/{deliveryId}")
    public ResponseEntity<List<LogsUserDeliveryDTO>> getLogsByDeliveryId(@PathVariable UUID deliveryId) {
        List<LogsUserDeliveryDTO> logs = logsUserDeliveryService.getLogsByDeliveryId(deliveryId);
        return ResponseEntity.ok(logs);
    }
} 