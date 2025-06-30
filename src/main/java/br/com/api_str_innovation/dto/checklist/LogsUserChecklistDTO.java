package br.com.api_str_innovation.dto.checklist;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogsUserChecklistDTO(
    UUID id,
    UUID userId,
    String userName,
    String action,
    UUID checklistId,
    UUID vehicleId,
    LocalDateTime actionDateTime,
    String description
) {
    
    public LogsUserChecklistDTO(UUID id, UUID userId, String userName, String action, 
                               UUID checklistId, UUID vehicleId, LocalDateTime actionDateTime, String description) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.action = action;
        this.checklistId = checklistId;
        this.vehicleId = vehicleId;
        this.actionDateTime = actionDateTime;
        this.description = description;
    }
} 