package br.com.api_str_innovation.dto.vehicle;


import br.com.api_str_innovation.entities.vehicle.VehicleEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleResponseDTO(UUID id,
                                 String licensePlateNumber,
                                 String sideNumber,
                                 String model,
                                 String brand,
                                 String yearDt,
                                 String status,
                                 LocalDateTime createdDt,
                                 LocalDateTime inactivatedDt,
                                 UUID driverId,
                                 UUID shippingManagerId,
                                 UUID generalManagerId) {

    public VehicleResponseDTO(VehicleEntity vehicle) {
        this (vehicle.getId(),
                vehicle.getLicensePlateNumber(),
                vehicle.getSideNumber(),
                vehicle.getModel(),
                vehicle.getBrand(),
                vehicle.getYearDt(),
                vehicle.getStatus(),
                vehicle.getCreatedDt(),
                vehicle.getInactivatedDt(),
                vehicle.getDriver() != null ? vehicle.getDriver().getId() : null,
                vehicle.getShippingManager() != null ? vehicle.getShippingManager().getId() : null,
                vehicle.getGeneralManager() != null ? vehicle.getGeneralManager().getId() : null
                );
    }
}
