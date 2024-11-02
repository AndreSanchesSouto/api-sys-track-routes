package br.com.api_str_innovation.dto.client;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(@NotBlank String name,
                               @NotBlank String contactType,
                               @NotBlank String contact,
                               @NotBlank String status) {
}
