package br.com.api_str_innovation.dto.data_address;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;

import java.util.UUID;

public record DataAddressResponseDTO(
        UUID id,
        String addressType,
        String street,
        String number,
        String zipCode,
        String neighborhood,
        String city,
        String state,
        String complement,
        String referencePoint
) {
    public DataAddressResponseDTO(DataAddressEntity data) {
        this(
            data.getId(),
            data.getAddressType(),
            data.getStreet(),
            data.getNumber(),
            data.getZipCode(),
            data.getNeighborhood(),
            data.getCity(),
            data.getState(),
            data.getComplement(),
            data.getReferencePoint()
        );
    }
}
