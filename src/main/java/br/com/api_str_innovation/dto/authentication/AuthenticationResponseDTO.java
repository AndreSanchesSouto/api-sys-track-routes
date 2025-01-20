package br.com.api_str_innovation.dto.authentication;

import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.Status;
import br.com.api_str_innovation.entities.user.UserEntity;
import lombok.Getter;

public class AuthenticationResponseDTO {

    @Getter
    private String token;
    private UserEntity employee;
    @Getter
    private UserResponseDTO response;


    public AuthenticationResponseDTO(String token, UserEntity employee) {
        this.token = token;
        this.response = new UserResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getLogin(),
                Status.valueOf(employee.getStatus().toUpperCase()),
                Role.valueOf(employee.getRole().toUpperCase())
        );
    }

}
