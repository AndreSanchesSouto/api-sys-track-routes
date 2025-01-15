package br.com.api_str_innovation.dto.employee;

import br.com.api_str_innovation.entities.employee.Role;
import br.com.api_str_innovation.entities.employee.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO (
        UUID id,
        String name,
        String email,
        String login,
        String status,
        Role role
){
    public static UserResponseDTO create(UserEntity user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getStatus(),
                user.getRole()
        );
    }
}
