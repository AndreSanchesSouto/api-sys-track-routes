package br.com.api_str_innovation.dto.vehicle;

import br.com.api_str_innovation.entities.vehicle.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record VehicleRequestDTO(
        @NotBlank
        String licensePlateNumber,
        @NotBlank
        String sideNumber,
        @NotBlank
        String model,
        @NotBlank
        String brand,
        @NotBlank
        String yearDt,
        @Nullable
        Status status
) { }
