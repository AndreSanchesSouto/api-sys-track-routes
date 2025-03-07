package br.com.api_str_innovation.dto.delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record DeliveryRequestDTO(

        @NotBlank(message = "O campo status não pode ser vazio")
        String status,

        @NotNull(message = "O campo clientId não pode ser vazio")
        UUID clientId,

        @NotNull(message = "O campo vehicleId não pode ser vazio")
        UUID vehicleId,

        @NotNull(message = "O campo productsId não pode ser vazio")
        List<DeliveryProductRequestDTO> products

) {

}
