package br.com.api_str_innovation.dto.authentication;

import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.UserStatus;
import br.com.api_str_innovation.entities.user.UserEntity;
import lombok.Getter;

public class AuthenticationResponseDTO {

    @Getter
    private String token;
    private UserEntity employee;
    @Getter
    private UserResponseDTO user;


    public AuthenticationResponseDTO(String token, UserEntity employee) {
        this.token = token;
        this.user = new UserResponseDTO(
                employee.getId(),
                employee.getImageUrl(),
                employee.getName(),
                employee.getEmail(),
                employee.getDocument(),
                employee.getLogin(),
                UserStatus.valueOf(employee.getStatus().toUpperCase()),
                employee.getGeneralManagerId(),
                Role.valueOf(employee.getRole().toUpperCase())
        );
    }

}
