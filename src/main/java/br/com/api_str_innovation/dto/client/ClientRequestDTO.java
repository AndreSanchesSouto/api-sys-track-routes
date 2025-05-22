package br.com.api_str_innovation.dto.client;

import br.com.api_str_innovation.infra.anotation.CnpjCpfAnotation;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

public record ClientRequestDTO(
        @Email
        String email,
        String name,
        @Nullable
        String document,
        String cellphone
) { }
