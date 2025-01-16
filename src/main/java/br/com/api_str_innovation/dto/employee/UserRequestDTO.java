package br.com.api_str_innovation.dto.employee;

import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
            @NotBlank
            String name,
            @NotBlank
            @Email
            String email,
            @NotBlank
            String login,
            @NotBlank
            String password,
            @NotBlank
            Status status,
            @NotBlank
            Role role
) {
    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", role=" + role +
                '}';
    }
}
