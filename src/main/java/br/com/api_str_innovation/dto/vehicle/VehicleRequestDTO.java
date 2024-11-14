package br.com.api_str_innovation.dto.vehicle;

public record VehicleRequestDTO(String licensePlateNumber,
                                String sideNumber,
                                String model,
                                String brand,
                                String yearDt,
                                String status) {
}
