package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.delivery.DeliveryRequestDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryResponseDTO;
import br.com.api_str_innovation.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository repository;

    public DeliveryResponseDTO post(DeliveryRequestDTO data) {
        return null;
    }

}
