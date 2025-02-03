package br.com.api_str_innovation.dto.product;

import br.com.api_str_innovation.entities.product.ProductEntity;
import java.time.LocalDate;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        String quantity,
        String unitValue,
        String price,
        String measure,
        LocalDate createdDt,
        LocalDate inactivatedDt
) {
    public ProductResponseDTO(ProductEntity data) {
        this(
            data.getId(),
            data.getName(),
            data.getDescription(),
            data.getQuantity().toString(),
            data.getUnitValue(),
            data.getPrice().toString(),
            data.getMeasure().toString(),
            data.getCreatedDt(),
            data.getInactivatedDt()
        );
    }
}
