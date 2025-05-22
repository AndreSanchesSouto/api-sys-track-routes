package br.com.api_str_innovation.dto.delivery;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.service.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DeliveryProductsResponseDTO(
         UUID id,
         String status,
         ClientEntity client,
         Integer deliveryRequest,
         UserEntity driver,
         List<DeliveryProductResponseDTO> deliveryProducts,
         VehicleEntity vehicle,
         DataAddressEntity address,
         BigDecimal latitude,
         BigDecimal longitude,
         LocalDate createdDt,
         LocalDate inactivatedDt
) {

    public DeliveryProductsResponseDTO(DeliveryEntity data, ProductService productService) {
        this(
                data.getId(),
                data.getStatus(),
                data.getClient(),
                data.getDeliveryRequest(),
                data.getDriver(),
                data.getDeliveryProducts()
                        .stream()
                        .map(deliveryProduct -> {
                            return new DeliveryProductResponseDTO(deliveryProduct, Integer.parseInt(productService.getById(deliveryProduct.getProductId()).quantity()));
                        })
                        .toList(),
                data.getVehicle(),
                data.getAddress(),
                data.getLongitude(),
                data.getLongitude(),
                data.getCreatedDt(),
                data.getInactivatedDt()
        );
    }

}
