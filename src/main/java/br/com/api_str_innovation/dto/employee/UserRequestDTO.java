package br.com.api_str_innovation.dto.employee;

import br.com.api_str_innovation.entities.employee.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
            @NotNull
            String name,
            @NotNull
            @Email
            String email,
            @NotNull
            String login,
            @NotNull
            String password,
            @NotNull
            String status,
            @NotNull
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
