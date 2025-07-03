package br.com.api_str_innovation.dto.delivery;


import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DeliveryProductResponseDTO(
         UUID id,
         Integer quantity,
         Integer totalQuantity,
         String name,
         String unitValue,
         BigDecimal price,
         UUID productId,
         Double measure,
         LocalDate createdDt
) {
    public DeliveryProductResponseDTO(DeliveryProductEntity deliveryProduct, Integer totalQuantity) {
        this(
                deliveryProduct.getId(),
                deliveryProduct.getQuantity(),
                totalQuantity,
                deliveryProduct.getName(),
                deliveryProduct.getUnitValue(),
                deliveryProduct.getPrice(),
                deliveryProduct.getProductId(),
                deliveryProduct.getMeasure(),
                deliveryProduct.getCreatedDt()
        );
    }
}