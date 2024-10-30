package br.com.api_str_innovation.dto.general_manager;

import jakarta.validation.constraints.NotBlank;

public record GeneralManagerRequestDTO(@NotBlank String name,
                                       @NotBlank String email,
                                       @NotBlank String login,
                                       @NotBlank String password) {
}
