package br.com.api_str_innovation.dto.delivery;

import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.product.ProductResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DeliveryRequestDTO(

        @NotBlank(message = "O campo status não pode ser vazio")
        String status,

        @NotBlank(message = "O campo client não pode ser vazio")
        ClientEntity client,

        @NotBlank(message = "O campo products não pode ser vazio")
        List<ProductEntity> products

) {}
