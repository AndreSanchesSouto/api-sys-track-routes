package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.authentication.AuthenticationRequestDTO;
import br.com.api_str_innovation.dto.authentication.AuthenticationResponseDTO;
import br.com.api_str_innovation.dto.user.UserRequestDTO;
import br.com.api_str_innovation.service.AuthorizationService;
import br.com.api_str_innovation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserService service;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO data) {
        return this.authorizationService.authEmployee(data);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRequestDTO data) {
        this.service.post(data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado com sucesso");
    }
}
