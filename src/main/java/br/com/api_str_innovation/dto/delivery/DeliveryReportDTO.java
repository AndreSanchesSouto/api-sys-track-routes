package br.com.api_str_innovation.dto.delivery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DeliveryReportDTO(
        Integer deliveryRequest,
        String clientName,
        List<ProductInfo> products,
        Double totalWeight,
        BigDecimal totalPaid,
        LocalDate createdDt
) {
    public record ProductInfo(
            String name,
            Integer quantity,
            Double weight,
            BigDecimal price
    ) {}
}