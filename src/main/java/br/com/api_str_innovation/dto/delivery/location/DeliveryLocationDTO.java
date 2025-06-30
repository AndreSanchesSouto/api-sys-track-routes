package br.com.api_str_innovation.dto.delivery.location;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record   DeliveryLocationDTO(
        @NotNull
        BigDecimal latitude,
        @NotNull
        BigDecimal longitude
) {
}
