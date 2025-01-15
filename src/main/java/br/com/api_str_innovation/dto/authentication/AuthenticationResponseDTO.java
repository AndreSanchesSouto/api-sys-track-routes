package br.com.api_str_innovation.dto.authentication;

import br.com.api_str_innovation.dto.employee.UserResponseDTO;
import br.com.api_str_innovation.entities.employee.UserEntity;
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
                employee.getStatus(),
                employee.getRole()
        );
    }

}
