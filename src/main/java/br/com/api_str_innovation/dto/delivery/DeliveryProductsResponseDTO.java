package br.com.api_str_innovation.dto.delivery;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DeliveryProductsResponseDTO(
         UUID id,
         String status,
         ClientEntity client,
         Integer deliveryRequest,
         UserEntity driver,
         List<DeliveryProductEntity> deliveryProducts,
         List<ProductEntity> products,
         VehicleEntity vehicle,
         DataAddressEntity address,
         LocalDate createdDt,
         LocalDate inactivatedDt
) {

    public DeliveryProductsResponseDTO(DeliveryEntity data, List<ProductEntity> products) {
        this(
                data.getId(),
                data.getStatus(),
                data.getClient(),
                data.getDeliveryRequest(),
                data.getDriver(),
                data.getDeliveryProducts(),
                products,
                data.getVehicle(),
                data.getAddress(),
                data.getCreatedDt(),
                data.getInactivatedDt()
        );
    }

}
