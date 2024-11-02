package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.data_address.DataAddressResponseDTO;
import br.com.api_str_innovation.repository.DataAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataAddressService {

    @Autowired
    private DataAddressRepository repository;

    public List<DataAddressResponseDTO> getAll() {
        List<DataAddressResponseDTO> address = repository
                .findAll()
                .stream()
                .map(DataAddressResponseDTO::new)
                .toList();
        return address;
    }

}
