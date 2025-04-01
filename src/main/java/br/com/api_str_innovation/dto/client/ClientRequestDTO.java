package br.com.api_str_innovation.dto.client;

public record ClientRequestDTO(
        String name,
        String email,
        String cellphone,
        String document
) { }
