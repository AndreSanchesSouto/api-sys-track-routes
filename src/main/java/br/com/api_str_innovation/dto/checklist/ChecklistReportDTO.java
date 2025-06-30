package br.com.api_str_innovation.dto.checklist;

import java.time.LocalDateTime;
import java.util.List;

public record ChecklistReportDTO(
    LocalDateTime createdDt,
    String vehicleLicensePlate,
    String driverName,
    List<String> problems,
    String observationNotes
) {} 