package br.com.api_str_innovation.dto.delivery;

import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.order.DeliveryEntity;
import br.com.api_str_innovation.entities.order_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DeliveryResponseDTO(
         UUID id,
         String status,
         ClientEntity client,
         List<DeliveryProductEntity> deliveryProducts,
         VehicleEntity vehicle,
         LocalDate createdDt,
         LocalDate inactivatedDt
) {

    public DeliveryResponseDTO(DeliveryEntity data) {
        this(
                data.getId(),
                data.getStatus(),
                data.getClient(),
                data.getDeliveryProducts(),
                data.getVehicle(),
                data.getCreatedDt(),
                data.getInactivatedDt()
        );
    }

}
