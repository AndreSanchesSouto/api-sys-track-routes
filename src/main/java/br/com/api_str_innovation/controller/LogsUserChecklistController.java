package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.checklist.LogsUserChecklistDTO;
import br.com.api_str_innovation.service.LogsUserChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/logs/checklist")
@CrossOrigin(origins = "*")
public class LogsUserChecklistController {
    
    @Autowired
    private LogsUserChecklistService logsUserChecklistService;

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<LogsUserChecklistDTO>> getLogsByVehicleId(@PathVariable UUID vehicleId) {
        List<LogsUserChecklistDTO> logs = logsUserChecklistService.getLogsByVehicleId(vehicleId);
        return ResponseEntity.ok(logs);
    }
} 