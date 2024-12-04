package br.com.api_str_innovation.dto.employee;

import br.com.api_str_innovation.entities.employee.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDTO {

    private String name;

    private String email;

    private String login;

    private String password;

    private Role role;
}
