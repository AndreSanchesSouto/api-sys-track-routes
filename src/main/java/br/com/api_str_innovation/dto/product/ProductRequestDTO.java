package br.com.api_str_innovation.dto.product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(
        @NotBlank(message = "O campo 'name' não pode estar vazio ou em branco.")
        String name,
        @Nullable
        String description,
        @NotBlank(message = "O campo 'quantity' não pode estar vazio ou em branco.")
        String quantity,
        @NotBlank(message = "O campo 'unitValue' não pode estar vazio ou em branco.")
        String unitValue,
        @NotBlank(message = "O campo 'price' não pode estar vazio ou em branco.")
        String price,
        @NotBlank(message = "O campo 'measure' não pode estar vazio ou em branco.")
        String measure
) { }
