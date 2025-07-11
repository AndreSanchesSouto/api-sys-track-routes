package br.com.api_str_innovation.dto.client;

import br.com.api_str_innovation.infrastructure.anotation.CnpjCpfAnotation;
import jakarta.validation.constraints.Email;

public record ClientRequestDTO(
        String name,
        @Email
        String email,
        String cellphone,
        @CnpjCpfAnotation
        String document
) { }
