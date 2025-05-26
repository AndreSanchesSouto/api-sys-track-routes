package br.com.api_str_innovation.dto.user;

import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.infra.anotation.CnpjCpfAnotation;
import jakarta.annotation.Nullable;
import br.com.api_str_innovation.entities.user.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record UserRequestDTO(
            @NotBlank
            String name,
            @NotBlank
            @Email
            String email,
            @Nullable
            @CnpjCpfAnotation
            String document,
            @NotBlank
            String login,
            @NotBlank
            String password,
            UserStatus status,
            @NotBlank
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
