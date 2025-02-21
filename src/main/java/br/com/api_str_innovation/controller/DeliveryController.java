package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.delivery.DeliveryRequestDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryResponseDTO;
import br.com.api_str_innovation.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService service;

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> post(@Valid @RequestBody DeliveryRequestDTO data) {
        System.out.print("dataaaaaaa");
        System.out.print(data.products());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.post(data));
    }

}
