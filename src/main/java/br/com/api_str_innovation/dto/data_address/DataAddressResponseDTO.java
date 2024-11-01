package br.com.api_str_innovation.dto.data_address;

import br.com.api_str_innovation.entities.address.DataAddressEntity;

import java.util.UUID;

public record DataAddressResponseDTO(UUID id,
                                     String address_type,
                                     String address,
                                     String number,
                                     String zip_code,
                                     String reference) {

    public DataAddressResponseDTO(DataAddressEntity data) {
        this(data.getId(),
                data.getAddress_type(),
                data.getAddress(),
                data.getNumber(),
                data.getZip_code(),
                data.getReference());
    }

}
