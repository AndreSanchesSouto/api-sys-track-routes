package br.com.api_str_innovation.dto.product;

import br.com.api_str_innovation.entities.product.ProductEntity;
import java.time.LocalDate;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        String quantity,
        String value,
        String price,
        String scale,
        LocalDate createdDt,
        LocalDate inactivatedDt
) {
    public ProductResponseDTO(ProductEntity data) {
        this(
            data.getId(),
            data.getName(),
            data.getDescription(),
            data.getQuantity().toString(),
            data.getValue().toString(),
            data.getPrice().toString(),
            data.getScale(),
            data.getCreatedDt(),
            data.getInactivatedDt()
        );
    }
}
