package br.com.api_str_innovation.dto.vehicle;


import br.com.api_str_innovation.entities.vehicle.VehicleEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleResponseDTO(UUID id,
                                 String licensePlateNumber,
                                 String sideNumber,
                                 String model,
                                 String brand,
                                 String status,
                                 LocalDateTime createdDt,
                                 LocalDateTime inactivatedDt) {

    public VehicleResponseDTO(VehicleEntity vehicle) {
        this (vehicle.getId(),
                vehicle.getLicensePlateNumber(),
                vehicle.getSideNumber(),
                vehicle.getModel(),
                vehicle.getBrand(),
                vehicle.getStatus(),
                vehicle.getCreatedDt(),
                vehicle.getInactivatedDt());
    }
}
