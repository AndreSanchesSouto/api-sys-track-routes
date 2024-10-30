package br.com.api_str_innovation.dto.driver;

import br.com.api_str_innovation.entities.employee.DriverDomain;

import java.util.Date;
import java.util.UUID;

public record DriverResponseDTO(UUID id,
                                String name,
                                String email,
                                String login,
                                String status,
                                Date creationDt,
                                Date inactivationDt) {

    public DriverResponseDTO(DriverDomain driver) {
        this(driver.getId(),
                driver.getName(),
                driver.getLogin(),
                driver.getEmail(),
                driver.getStatus(),
                driver.getCreationDt(),
                driver.getInactivationDt());
    }

}