package br.com.api_str_innovation.dto.user;

import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.UserStatus;
import br.com.api_str_innovation.entities.user.UserEntity;

import java.util.UUID;

public record UserResponseDTO (
        UUID id,
        String name,
        String email,
        String login,
        UserStatus status,
        UUID generalManagerId,
        Role role
){
    public UserResponseDTO (UserEntity user) {
        this(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getLogin(),
            UserStatus.valueOf(user.getStatus().toUpperCase()),
            user.getGeneralManagerId(),
            Role.valueOf(user.getRole().toUpperCase())
        );
    }
}
