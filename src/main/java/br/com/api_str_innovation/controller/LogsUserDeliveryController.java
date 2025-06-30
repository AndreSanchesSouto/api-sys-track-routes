package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.delivery.delivery_user_log.LogsUserDeliveryDTO;
import br.com.api_str_innovation.dto.product.product_user_log.LogsUserDeliveryProductDTO;
import br.com.api_str_innovation.service.LogsUserDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/details/{logId}")
    public ResponseEntity<List<LogsUserDeliveryProductDTO>> getLogProducts(@PathVariable UUID logId) {
        List<LogsUserDeliveryProductDTO> products = logsUserDeliveryService.getProductsByLogId(logId);
        return ResponseEntity.ok(products);
    }
}