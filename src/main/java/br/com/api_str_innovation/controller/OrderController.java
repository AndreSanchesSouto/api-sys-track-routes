package br.com.api_str_innovation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public ResponseEntity<OrderResponseDTO> post(@Valid @RequestBody OrderRequestDTO) {

    }

}
