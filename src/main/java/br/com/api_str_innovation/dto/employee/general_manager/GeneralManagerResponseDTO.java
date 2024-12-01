package br.com.api_str_innovation.dto.employee.general_manager;

import br.com.api_str_innovation.entities.employee.GeneralManagerEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record GeneralManagerResponseDTO(UUID id,
                                        String name,
                                        String email,
                                        String login,
                                        String password,
                                        LocalDateTime createdDt,
                                        LocalDateTime inactivatedDt) {

    public GeneralManagerResponseDTO(GeneralManagerEntity data) {
        this(data.getId(),
                data.getName(),
                data.getEmail(),
                data.getLogin(),
                data.getPassword(),
                data.getCreatedDt(),
                data.getInactivatedDt());
    }
}
