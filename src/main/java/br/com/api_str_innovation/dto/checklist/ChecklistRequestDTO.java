package br.com.api_str_innovation.dto.checklist;

import java.util.UUID;

public record ChecklistRequestDTO(String tire,
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
                                  UUID employeeId,
                                  String employeeRole) {
}
