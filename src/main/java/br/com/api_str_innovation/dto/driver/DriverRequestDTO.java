package br.com.api_str_innovation.dto.driver;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DriverRequestDTO(@NotBlank String name,
                               @Email String email,
                               @NotBlank String login,
                               @NotNull String password,
                               String status) {
}
