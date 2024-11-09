package br.com.api_str_innovation.dto.client;

import br.com.api_str_innovation.entities.client.ClientEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponseDTO(UUID id,
                                String name,
                                String contactType,
                                String contact,
                                LocalDateTime created_dt,
                                LocalDateTime inactivated_dt) {

    public ClientResponseDTO(ClientEntity data) {
        this(data.getId(),
                data.getName(),
                data.getContactType(),
                data.getContact(),
                data.getCreatedDt(),
                data.getInactivatedDt());
    }

}