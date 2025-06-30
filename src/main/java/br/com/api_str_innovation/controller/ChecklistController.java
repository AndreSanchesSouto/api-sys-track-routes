package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistReportDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {

    @Autowired
    private ChecklistService service;

    @GetMapping("/get-by-vehicle-id/{vehicleId}")
    public ResponseEntity<ChecklistResponseDTO> getByVehicleId(@PathVariable UUID vehicleId) {
        ChecklistResponseDTO checklist = this.service.getByVehicleId(vehicleId);
        return ResponseEntity.ok(checklist);
    }

    @PostMapping("/count-km/{vehicleId}")
    public ResponseEntity<List<Object[]>> countKmDriven(@PathVariable UUID vehicleId, @RequestBody PeriodTimeRequestDTO data) {
        List<Object[]> kmDriven = this.service.countKmDriven(vehicleId, data);
        return ResponseEntity.ok(kmDriven);
    }

    @PostMapping("/km-segments/{vehicleId}")
    public ResponseEntity<List<Object[]>> getDetailedKmSegments(@PathVariable UUID vehicleId, @RequestBody PeriodTimeRequestDTO data) {
        List<Object[]> segments = this.service.getDetailedKmSegments(vehicleId, data);
        return ResponseEntity.ok(segments);
    }

    @PostMapping("/report-failed/{vehicleId}")
    public ResponseEntity<List<ChecklistReportDTO>> getChecklistReport(@PathVariable UUID vehicleId, @RequestHeader("general-manager-id") UUID generalManagerId, @RequestBody PeriodTimeRequestDTO data) {
        List<ChecklistReportDTO> reportFailedData = this.service.getChecklistReport(vehicleId, generalManagerId, data);
        return ResponseEntity.ok(reportFailedData);
    }

    @PostMapping("/{vehicleId}")
    public ResponseEntity<String> post(@PathVariable UUID vehicleId, @RequestBody ChecklistRequestDTO data) {
        this.service.post(vehicleId, data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Criado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Deletado");
    }

    @GetMapping
    public ResponseEntity<List<ChecklistResponseDTO>> getAll(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll(generalManagerId));
    }
}
