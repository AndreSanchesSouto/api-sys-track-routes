package br.com.api_str_innovation.dto.client;

import br.com.api_str_innovation.infra.anotation.CnpjCpfAnotation;
import jakarta.validation.constraints.Email;

public record ClientRequestDTO(
        @Email
        String name,
        String email,
        @CnpjCpfAnotation
        String cellphone,
        String document
) { }
