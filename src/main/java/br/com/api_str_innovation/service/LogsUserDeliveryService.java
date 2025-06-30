package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.delivery.LogsUserDeliveryDTO;
import br.com.api_str_innovation.entities.delivery.LogsUserDeliveryEntity;
import br.com.api_str_innovation.repository.LogsUserDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogsUserDeliveryService {
    
    @Autowired
    private LogsUserDeliveryRepository logsUserDeliveryRepository;

    public List<LogsUserDeliveryDTO> getLogsByDeliveryId(UUID deliveryId) {
        List<LogsUserDeliveryEntity> logs = logsUserDeliveryRepository.findByDeliveryIdOrderByActionDateTimeDesc(deliveryId);
        return logs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LogsUserDeliveryDTO convertToDTO(LogsUserDeliveryEntity entity) {
        return new LogsUserDeliveryDTO(
            entity.getId(),
            entity.getUserId(),
            entity.getUserName(),
            entity.getAction(),
            entity.getDeliveryId(),
            entity.getActionDateTime(),
            entity.getDescription()
        );
    }
} 