package br.com.api_str_innovation.dtos;

import jakarta.validation.constraints.NotBlank;

public record VehicleRecordDTO(@NotBlank String licensePlateNumber,
                               @NotBlank String sideNumber,
                               @NotBlank String model,
                               @NotBlank String brand) {
}
