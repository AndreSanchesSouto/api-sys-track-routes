package br.com.api_str_innovation.dto.client;

public record ClientRequestDTO(String name,
                               String contactType,
                               String contact,
                               String status) {
}
