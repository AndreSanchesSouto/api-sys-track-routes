package br.com.api_str_innovation.dto.user;

import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.Status;
import br.com.api_str_innovation.entities.user.UserEntity;

import java.util.UUID;

public record UserResponseDTO (
        UUID id,
        String name,
        String email,
        String login,
        Status status,
        Role role
){
    public UserResponseDTO (UserEntity user) {
        this(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getLogin(),
            Status.valueOf(user.getStatus().toUpperCase()),
            Role.valueOf(user.getRole().toUpperCase())
        );
    }
}
