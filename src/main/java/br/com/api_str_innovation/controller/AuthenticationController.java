package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.authentication.AuthenticationRequestDTO;
import br.com.api_str_innovation.dto.authentication.AuthenticationResponseDTO;
import br.com.api_str_innovation.dto.employee.ResponseDTO;
import br.com.api_str_innovation.dto.employee.general_manager.GeneralManagerRequestDTO;
import br.com.api_str_innovation.service.AuthorizationService;
import br.com.api_str_innovation.service.GeneralManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private GeneralManagerService service;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO data) {
        return this.authorizationService.authEmployee(data);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid GeneralManagerRequestDTO data) {
        return this.service.post(data);
    }

    @GetMapping
    public String verify() {
        return "Okay";
    }

}
