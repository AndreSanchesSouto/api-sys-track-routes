package br.com.api_str_innovation.dto.authentication;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequestDTO(
        @NotNull
        String login,
        @NotNull
        String password
) { }
