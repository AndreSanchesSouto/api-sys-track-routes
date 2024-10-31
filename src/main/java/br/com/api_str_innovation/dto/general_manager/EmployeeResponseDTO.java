package br.com.api_str_innovation.dto.general_manager;

import br.com.api_str_innovation.entities.employee.GeneralManagerEntity;

import java.util.Date;
import java.util.UUID;

public record EmployeeResponseDTO(UUID id,
                                  String name,
                                  String email,
                                  String login,
                                  String password,
                                  Date creationDt,
                                  Date inactivationDt) {

    public EmployeeResponseDTO(GeneralManagerEntity data) {
        this(data.getId(),
                data.getName(),
                data.getEmail(),
                data.getLogin(),
                data.getPassword(),
                data.getCreationDt(),
                data.getInactivationDt());
    }
}
