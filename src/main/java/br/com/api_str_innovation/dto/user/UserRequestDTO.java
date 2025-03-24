package br.com.api_str_innovation.dto.user;

import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
            @NotBlank
            String name,
            @NotBlank
            @Email
            String email,
            @Nullable
            String document,
            @NotBlank
            String login,
            @NotBlank
            String password,
            @NotNull
            Status status,
            @NotNull
            Role role
) {
    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", document='" + document + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", role=" + role +
                '}';
    }
}
