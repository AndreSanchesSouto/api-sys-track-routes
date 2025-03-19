package br.com.api_str_innovation.dto.delivery;

import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DeliveryGenericResponseDTO(
         UUID id,
         String status,
         ClientEntity client,
         VehicleEntity vehicle,
         UserEntity driver,
         Integer items,
         LocalDate createdDt,
         LocalDate inactivatedDt
) {

    public DeliveryGenericResponseDTO(DeliveryEntity data) {
        this(
                data.getId(),
                data.getStatus(),
                data.getClient(),
                data.getVehicle(),
                data.getDriver(),
                data.getDeliveryProducts().toArray().length,
                data.getCreatedDt(),
                data.getInactivatedDt()
        );
    }

}
