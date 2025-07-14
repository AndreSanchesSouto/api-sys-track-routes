package br.com.api_str_innovation.dto.client;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClientResponseDTO(UUID id,
                                String image,
                                String name,
                                String document,
                                String email,
                                String cellphone,
                                @Nullable
                                List<DataAddressEntity> addresses,
                                LocalDateTime created_dt,
                                LocalDateTime inactivated_dt) {

    public ClientResponseDTO(ClientEntity data) {
        this(
                data.getId(),
                data.getImageUrl(),
                data.getName(),
                data.getDocument(),
                data.getEmail(),
                data.getCellphone(),
                data.getAddresses(),
                data.getCreatedDt(),
                data.getInactivatedDt());
    }

}