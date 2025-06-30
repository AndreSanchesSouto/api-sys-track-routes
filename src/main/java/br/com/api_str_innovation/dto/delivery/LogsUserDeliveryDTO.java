package br.com.api_str_innovation.dto.delivery;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogsUserDeliveryDTO(
    UUID id,
    UUID userId,
    String userName,
    String action,
    UUID deliveryId,
    LocalDateTime actionDateTime,
    String description
) {
    
    public LogsUserDeliveryDTO(UUID id, UUID userId, String userName, String action, 
                              UUID deliveryId, LocalDateTime actionDateTime, String description) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.action = action;
        this.deliveryId = deliveryId;
        this.actionDateTime = actionDateTime;
        this.description = description;
    }
} 