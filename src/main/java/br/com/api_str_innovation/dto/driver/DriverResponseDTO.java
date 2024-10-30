package br.com.api_str_innovation.dto.driver;

import br.com.api_str_innovation.entities.employee.DriverEntity;

import java.util.Date;
import java.util.UUID;

public record DriverResponseDTO(UUID id,
                                String name,
                                String email,
                                String login,
                                String status,
                                Date creationDt,
                                Date inactivationDt) {

    public DriverResponseDTO(DriverEntity driver) {
        this(driver.getId(),
                driver.getName(),
                driver.getLogin(),
                driver.getEmail(),
                driver.getStatus(),
                driver.getCreationDt(),
                driver.getInactivationDt());
    }

}