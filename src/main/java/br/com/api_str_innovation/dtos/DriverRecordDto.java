package br.com.api_str_innovation.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DriverRecordDto(@NotBlank String name,
                              @NotBlank @Email String email,
                              @NotBlank String login,
                              @NotBlank String password,
                              @NotBlank String status) {
}
