package br.com.api_str_innovation.dto.employee.driver;

import br.com.api_str_innovation.entities.employee.DriverEntity;
import br.com.api_str_innovation.entities.employee.Role;

import java.time.LocalDateTime;
import java.util.UUID;
public record DriverResponseDTO(UUID id,
                                String name,
                                String email,
                                String login,
                                String status,
                                Role role,
                                LocalDateTime createdDt,
                                LocalDateTime inactivatedDt) {

    public DriverResponseDTO(DriverEntity driver) {
        this(
            driver.getId(),
            driver.getName(),
            driver.getEmail(),
            driver.getLogin(),
            driver.getStatus(),
            driver.getRole(),
            driver.getCreatedDt(),
            driver.getInactivatedDt());
    }

}