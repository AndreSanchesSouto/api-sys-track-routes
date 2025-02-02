package br.com.api_str_innovation.dto.vehicle;


import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.vehicle.Status;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleResponseDTO(UUID id,
                                 String licensePlateNumber,
                                 String sideNumber,
                                 String model,
                                 String brand,
                                 String yearDt,
                                 Status status,
                                 ChecklistEntity checklist,
                                 LocalDateTime createdDt,
                                 LocalDateTime inactivatedDt) {

    public VehicleResponseDTO(VehicleEntity vehicle) {
        this (vehicle.getId(),
                vehicle.getLicensePlateNumber(),
                vehicle.getSideNumber(),
                vehicle.getModel(),
                vehicle.getBrand(),
                vehicle.getYearDt(),
                Status.valueOf(vehicle.getStatus().toUpperCase()),
                vehicle.getChecklist(),
                vehicle.getCreatedDt(),
                vehicle.getInactivatedDt());
    }
}
