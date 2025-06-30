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
        String licensePlateNumber,
        LocalDateTime actionDateTime,
        String description
) {}