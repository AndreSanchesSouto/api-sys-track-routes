package br.com.api_str_innovation.dto.authentication;

import br.com.api_str_innovation.dto.employee.general_manager.GeneralManagerResponseDTO;
import br.com.api_str_innovation.entities.employee.AbstractEmployeeEntity;
import lombok.Getter;

public class AuthenticationResponseDTO {

    @Getter
    private String token;
    private AbstractEmployeeEntity employee;
    private GeneralManagerResponseDTO response;

    public AuthenticationResponseDTO(String token, AbstractEmployeeEntity employee) {
        this.token = token;
        this.response = new GeneralManagerResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getLogin(),
                employee.getPassword(),
                employee.getRole(),
                employee.getCreatedDt(),
                employee.getInactivatedDt()
        );
    }

    public Object getEmployee() {
        return response;
    }
}
