package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.delivery.delivery_user_log.LogsUserDeliveryDTO;
import br.com.api_str_innovation.dto.product.product_user_log.LogsUserDeliveryProductDTO;
import br.com.api_str_innovation.entities.delivery.delivery_user_log.LogsUserDeliveryEntity;
import br.com.api_str_innovation.repository.LogsUserDeliveryProductRepository;
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

    @Autowired
    private LogsUserDeliveryProductRepository logsUserDeliveryProductRepository;

    public List<LogsUserDeliveryDTO> getLogsByDeliveryId(UUID deliveryId) {
        List<LogsUserDeliveryEntity> logs = logsUserDeliveryRepository.findByDeliveryIdOrderByActionDateTimeDesc(deliveryId);
        return logs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LogsUserDeliveryProductDTO> getProductsByLogId(UUID logId) {
        return logsUserDeliveryProductRepository.findByLogId(logId)
                .stream()
                .map(product -> new LogsUserDeliveryProductDTO(
                        product.getId(),
                        product.getProductId(),
                        product.getName(),
                        product.getQuantity(),
                        product.getMeasure(),
                        product.getUnit()
                ))
                .toList();
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