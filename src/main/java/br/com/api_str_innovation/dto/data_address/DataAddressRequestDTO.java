package br.com.api_str_innovation.dto.data_address;

import jakarta.validation.constraints.NotBlank;

public record DataAddressRequestDTO(@NotBlank String address_type,
                                    @NotBlank String address,
                                    @NotBlank String number,
                                    @NotBlank String zip_code,
                                    @NotBlank String reference) {
}
