package br.com.api_str_innovation.dto.delivery;

import java.util.List;

public record DeliveryReportDTO(
        Integer deliveryRequest,
        String clientName,
        List<ProductInfo> products,
        Double totalWeight,
        Double totalPaid
) {
    public record ProductInfo(
            String name,
            Integer quantity,
            Double weight,
            Double price
    ) {}
}