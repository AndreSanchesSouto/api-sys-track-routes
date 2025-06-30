package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.LogsUserChecklistDTO;
import br.com.api_str_innovation.entities.checklist.LogsUserChecklistEntity;
import br.com.api_str_innovation.repository.LogsUserChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogsUserChecklistService {
    
    @Autowired
    private LogsUserChecklistRepository logsUserChecklistRepository;

    public List<LogsUserChecklistDTO> getLogsByVehicleId(UUID vehicleId) {
        List<LogsUserChecklistEntity> logs = logsUserChecklistRepository.findByVehicleIdOrderByActionDateTimeDesc(vehicleId);
        return logs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LogsUserChecklistDTO convertToDTO(LogsUserChecklistEntity entity) {
        return new LogsUserChecklistDTO(
            entity.getId(),
            entity.getUserId(),
            entity.getUserName(),
            entity.getAction(),
            entity.getChecklistId(),
            entity.getVehicleId(),
            entity.getActionDateTime(),
            entity.getDescription()
        );
    }
} 