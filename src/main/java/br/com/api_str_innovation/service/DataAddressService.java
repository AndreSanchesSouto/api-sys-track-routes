package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.data_address.DataAddressRequestDTO;
import br.com.api_str_innovation.dto.data_address.DataAddressResponseDTO;
import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.exceptions.DataAddressException;
import br.com.api_str_innovation.repository.DataAddressRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class DataAddressService {

    @Autowired
    private DataAddressRepository repository;

    @Autowired
    private ClientService clientService;

    public List<DataAddressResponseDTO> getAll() {
        List<DataAddressResponseDTO> address = repository
                .findAll()
                .stream()
                .map(DataAddressResponseDTO::new)
                .toList();
        return address;
    }

    public List<DataAddressResponseDTO> getByClientId(UUID clientId) {
        return repository
                .getAllDataAddressByClientId(clientId)
                .stream()
                .map(DataAddressResponseDTO::new)
                .toList();
    }

    public DataAddressResponseDTO getById(UUID id) {
        DataAddressEntity data =  repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        return new DataAddressResponseDTO(data);

    }

    public DataAddressResponseDTO post(UUID idClient, @RequestBody DataAddressRequestDTO data) {
        ClientEntity client = clientService.getById(idClient);
        DataAddressEntity address = new DataAddressEntity(data, client);
        repository.save(address);

        return new DataAddressResponseDTO(address);
    }

    public DataAddressResponseDTO put(UUID id, @RequestBody DataAddressRequestDTO data) {
        DataAddressEntity dataAddress = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        dataAddress.setStreet(data.street());
        dataAddress.setZipCode(data.zipCode());
        dataAddress.setStreet(data.street());
        dataAddress.setNumber(data.number());
        dataAddress.setAddressType(data.addressType());
        dataAddress.setNeighborhood(data.neighborhood());
        dataAddress.setCity(data.city());
        dataAddress.setState(data.state());
        dataAddress.setComplement(data.complement());
        dataAddress.setReferencePoint(data.referencePoint());

        this.repository.save(dataAddress);

        return new DataAddressResponseDTO(dataAddress);
    }

}
