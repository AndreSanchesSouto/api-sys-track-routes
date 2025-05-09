package br.com.api_str_innovation.dto.checklist.checklist_log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ChecklistLogResponseDTO(UUID id,
                                      Boolean tire,
                                      String spareTire,
                                      Double kilometersNumber,
                                      Double fuelLevel,
                                      Double oilLevel,
                                      Double waterLevel,
                                      Boolean suspension,
                                      String brakes,
                                      Boolean glasses,
                                      Boolean windshieldWipers,
                                      Boolean rearview,
                                      Boolean headlight,
                                      Boolean taillight,
                                      Boolean frontIndicator,
                                      Boolean indicator,
                                      Boolean domeLight,
                                      Boolean licensePlateLight,
                                      String tirePressure,
                                      String jack,
                                      String toolbox,
                                      String documentation,
                                      String observationNotes,
                                      LocalDate creationDt,
                                      LocalDate editedDt,
                                      UUID vehicle_id,
                                      UUID employee_id,
                                      UUID employee_role) {
}
