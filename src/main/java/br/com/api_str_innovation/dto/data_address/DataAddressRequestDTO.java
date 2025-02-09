package br.com.api_str_innovation.dto.data_address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Setter;

public record DataAddressRequestDTO(

        @NotBlank(message = "O campo CEP não pode estar vazio")
        @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres")
        String zipCode,

        @NotBlank(message = "O campo endereço não pode estar vazio")
        String address,

        @NotBlank(message = "O campo número não pode estar vazio")
        String number,

        @NotBlank(message = "O campo tipo de endereço não pode estar vazio")
        String addressType,

        @NotBlank(message = "O campo bairro não pode estar vazio")
        String neighborhood,

        @NotBlank(message = "O campo cidade não pode estar vazio")
        String city,

        @NotBlank(message = "O campo estado não pode estar vazio")
        String state,

        String complement,

        String referencePoint

) { }
