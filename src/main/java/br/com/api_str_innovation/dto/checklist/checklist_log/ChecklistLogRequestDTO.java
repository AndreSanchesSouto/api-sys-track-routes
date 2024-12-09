package br.com.api_str_innovation.dto.checklist.checklist_log;

import java.time.LocalDate;

public record ChecklistLogRequestDTO(LocalDate startDate,
                                     LocalDate endDate) {
}
