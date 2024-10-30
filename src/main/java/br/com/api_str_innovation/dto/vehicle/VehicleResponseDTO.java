package br.com.api_str_innovation.dto.vehicle;


import br.com.api_str_innovation.entities.vehicle.VehicleDomain;

import java.util.UUID;

public record VehicleResponseDTO(UUID id,
                                 String licensePlateNumber,
                                 String sideNumber,
                                 String model,
                                 String brand,
                                 String status) {

    public VehicleResponseDTO(VehicleDomain vehicle) {
        this (vehicle.getId(),
                vehicle.getLicensePlateNumber(),
                vehicle.getSideNumber(),
                vehicle.getModel(),
                vehicle.getBrand(),
                vehicle.getStatus());
    }
}
