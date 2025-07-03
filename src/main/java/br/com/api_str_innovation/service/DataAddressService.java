package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.data_address.DataAddressRequestDTO;
import br.com.api_str_innovation.dto.data_address.DataAddressResponseDTO;
import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.exceptions.DataAddressException;
import br.com.api_str_innovation.repository.DataAddressRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DataAddressService {

    @Autowired
    private DataAddressRepository repository;

    @Autowired
    private ClientService clientService;

    @Lazy
    @Autowired
    private DeliveryService deliveryService;

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
        DataAddressEntity data =  this
                .findById(id);
        return new DataAddressResponseDTO(data);
    }

    public DataAddressEntity findById(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

    }

    public DataAddressResponseDTO post(UUID idClient, @RequestBody DataAddressRequestDTO data) {
        ClientEntity client = clientService.getById(idClient);
        DataAddressEntity address = new DataAddressEntity(data, client);
        repository.save(address);

        return new DataAddressResponseDTO(address);
    }

    public DataAddressResponseDTO patch(UUID id, @RequestBody DataAddressRequestDTO data) {
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
        dataAddress.setLatitude(new BigDecimal(data.latitude()));
        dataAddress.setLongitude(new BigDecimal(data.longitude()));

        this.repository.save(dataAddress);

        return new DataAddressResponseDTO(dataAddress);
    }

    public ResponseEntity<Void> delete(UUID id) {
        DataAddressEntity address = this.findById(id);
        if(deliveryService.findActiveByAddressId(id)!=null) {
            throw new DataAddressException("Endereço em uso para uma ou mais entregas");
        }
        address.setInactivatedDt(LocalDate.now());
        repository.save(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
