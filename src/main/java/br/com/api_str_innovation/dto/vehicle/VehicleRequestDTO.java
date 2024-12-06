package br.com.api_str_innovation.dto.vehicle;

import br.com.api_str_innovation.entities.employee.DriverEntity;
import br.com.api_str_innovation.entities.employee.GeneralManagerEntity;
import br.com.api_str_innovation.entities.employee.ShippingManagerEntity;

public record VehicleRequestDTO(String licensePlateNumber,
                                String sideNumber,
                                String model,
                                String brand,
                                String yearDt,
                                String status,
                                DriverEntity driver,
                                ShippingManagerEntity shippingManager,
                                GeneralManagerEntity generalManager) {
}
