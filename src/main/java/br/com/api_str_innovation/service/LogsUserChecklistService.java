package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.LogsUserChecklistDTO;
import br.com.api_str_innovation.entities.checklist.LogsUserChecklistEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.repository.LogsUserChecklistRepository;
import br.com.api_str_innovation.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogsUserChecklistService {
    
    @Autowired
    private LogsUserChecklistRepository logsUserChecklistRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<LogsUserChecklistDTO> getLogsByVehicleId(UUID vehicleId) {
        List<LogsUserChecklistEntity> logs = logsUserChecklistRepository.findByVehicleIdOrderByActionDateTimeDesc(vehicleId);

        String licensePlate = vehicleRepository.findById(vehicleId)
                .map(VehicleEntity::getLicensePlateNumber)
                .orElse(null);

        return logs.stream()
                .map(log -> new LogsUserChecklistDTO(
                        log.getId(),
                        log.getUserId(),
                        log.getUserName(),
                        log.getAction(),
                        log.getChecklistId(),
                        log.getVehicleId(),
                        licensePlate,
                        log.getActionDateTime(),
                        log.getDescription()
                ))
                .collect(Collectors.toList());
    }
} 