package br.com.api_str_innovation.dto.product.product_user_log;

import java.util.UUID;

public record LogsUserDeliveryProductDTO(
        UUID id,
        UUID productId,
        String name,
        Integer quantity,
        Double measure,
        String unit,
        String actionLog
) {}