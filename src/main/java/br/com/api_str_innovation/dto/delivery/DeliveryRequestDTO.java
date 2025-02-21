package br.com.api_str_innovation.dto.delivery;

import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.product.ProductResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DeliveryRequestDTO(

        @NotBlank(message = "O campo status não pode ser vazio")
        String status,

        @NotNull(message = "O campo client não pode ser vazio")
        ClientEntity client,

        @NotNull(message = "O campo products não pode ser vazio")
        List<ProductEntity> products

) {

}
