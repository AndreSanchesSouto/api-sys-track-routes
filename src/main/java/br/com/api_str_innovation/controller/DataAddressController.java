package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.data_address.DataAddressResponseDTO;
import br.com.api_str_innovation.service.DataAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class DataAddressController {

    @Autowired
    private DataAddressService service;

    @GetMapping
    private ResponseEntity<List<DataAddressResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

}
