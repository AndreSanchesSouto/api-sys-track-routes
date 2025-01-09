package br.com.api_str_innovation.dto.vehicle;

import br.com.api_str_innovation.entities.employee.UserEntity;

public record VehicleRequestDTO(String licensePlateNumber,
                                String sideNumber,
                                String model,
                                String brand,
                                String yearDt,
                                String status) {
}
