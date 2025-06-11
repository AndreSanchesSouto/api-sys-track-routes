package br.com.api_str_innovation.dto.delivery;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.service.ProductService;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record DeliveryProductsResponseDTO(
         UUID id,
         String status,
         ClientEntity client,
         Integer deliveryRequest,
         UserEntity driver,
         List<DeliveryProductResponseDTO> deliveryProducts,
         VehicleEntity vehicle,
         String checklistDate,
         UUID employeeIdChecklistDoneBy,
         DataAddressEntity address,
         LocalDate createdDt,
         LocalDate inactivatedDt
) {

    public DeliveryProductsResponseDTO(DeliveryEntity data, List<DeliveryProductResponseDTO> deliveryProducts, String checklistDate, UUID employeeId) {
        this(
                data.getId(),
                data.getStatus().toUpperCase(),
                data.getClient(),
                data.getDeliveryRequest(),
                data.getDriver(),
                deliveryProducts,
                data.getVehicle(),
                checklistDate,
                employeeId,
                data.getAddress(),
                data.getCreatedDt(),
                data.getInactivatedDt()
        );
    }
    public DeliveryProductsResponseDTO(DeliveryEntity data, List<DeliveryProductResponseDTO> deliveryProducts) {
        this(
                data.getId(),
                data.getStatus(),
                data.getClient(),
                data.getDeliveryRequest(),
                data.getDriver(),
                deliveryProducts,
                data.getVehicle(),
                null,
                null,
                data.getAddress(),
                data.getCreatedDt(),
                data.getInactivatedDt()
        );
    }
}
