package br.com.api_str_innovation.dto.checklist.checklist_log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ChecklistLogResponseDTO(UUID id,
                                      String tire,
                                      String licensePlateNumber,
                                      String spareTire,
                                      Double kilometersNumber,
                                      Double fuelLevel,
                                      Double oilLevel,
                                      Double waterLevel,
                                      String suspension,
                                      String brakes,
                                      String lights,
                                      String glasses,
                                      String windshieldWipers,
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
