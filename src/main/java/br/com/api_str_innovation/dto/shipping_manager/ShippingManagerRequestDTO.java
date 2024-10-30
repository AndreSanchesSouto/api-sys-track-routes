package br.com.api_str_innovation.dto.shipping_manager;

import jakarta.validation.constraints.NotBlank;

public record ShippingManagerRequestDTO(@NotBlank String name,
                                        @NotBlank String email,
                                        @NotBlank String login,
                                        @NotBlank String password) {
}
