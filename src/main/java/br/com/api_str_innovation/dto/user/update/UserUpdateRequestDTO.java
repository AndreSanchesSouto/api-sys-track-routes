package br.com.api_str_innovation.dto.user.update;

import br.com.api_str_innovation.entities.user.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String login,
        String password,
        @NotNull
        UserStatus status
) {}