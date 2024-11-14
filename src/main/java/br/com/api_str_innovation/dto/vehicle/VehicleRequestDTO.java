package br.com.api_str_innovation.dto.vehicle;

import jakarta.validation.constraints.NotBlank;

public record VehicleRequestDTO(@NotBlank String licensePlateNumber,
                                @NotBlank String sideNumber,
                                @NotBlank String model,
                                @NotBlank String brand,
                                @NotBlank String yearDt,
                                String status) {
}
