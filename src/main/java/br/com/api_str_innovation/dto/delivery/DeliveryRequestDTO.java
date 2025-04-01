package br.com.api_str_innovation.dto.delivery;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record DeliveryRequestDTO(

        @Nullable
        String status,

        @NotNull(message = "O campo driverId não pode ser vazio")
        UUID driverId,

        @NotNull(message = "O campo clientId não pode ser vazio")
        UUID clientId,

        @NotNull(message = "O campo addressId não pode ser vazio")
        UUID addressId,

        @NotNull(message = "O campo vehicleId não pode ser vazio")
        UUID vehicleId,

        @NotNull(message = "O campo productsId não pode ser vazio")
        List<DeliveryProductRequestDTO> products

) { }
