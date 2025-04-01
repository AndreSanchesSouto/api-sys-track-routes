package br.com.api_str_innovation.dto.delivery;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeliveryProductRequestDTO(
        @NotNull(message = "productId não pode ser nulo")
        UUID productId,

        @NotNull(message = "quantidade não pode ser nula")
        Integer quantity
) {}
