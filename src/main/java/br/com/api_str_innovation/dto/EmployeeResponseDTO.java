package br.com.api_str_innovation.dto;

import br.com.api_str_innovation.entities.employee.AbstractEmployeeEntity;

import java.util.Date;
import java.util.UUID;

public record EmployeeResponseDTO(UUID id,
                                  String name,
                                  String email,
                                  String login,
                                  String status,
                                  Date creationDt,
                                  Date inactivationDt) {

    public EmployeeResponseDTO (AbstractEmployeeEntity abstractEmployee) {
        this(abstractEmployee.getId(),
                abstractEmployee.getName(),
                abstractEmployee.getEmail(),
                abstractEmployee.getLogin(),
                abstractEmployee.getStatus(),
                abstractEmployee.getCreationDt(),
                abstractEmployee.getInactivationDt());
    }
}
