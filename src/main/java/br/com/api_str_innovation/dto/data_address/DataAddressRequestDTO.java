package br.com.api_str_innovation.dto.data_address;

public record DataAddressRequestDTO(String address_type,
                                    String address,
                                    String number,
                                    String zip_code,
                                    String reference) {
}
