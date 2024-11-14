package br.com.api_str_innovation.dto.checklist;

import lombok.Getter;

public record ChecklistRequestDTO(String tire,
                                  String licensePlateNumber,
                                  String spareTire,
                                  @Getter String kilometersNumber,
                                  String fuelLevel,
                                  String oilLevel,
                                  String waterLevel,
                                  String suspension,
                                  String brakes,
                                  String lights,
                                  String glasses,
                                  String windshieldWipers,
                                  String jack,
                                  String toolbox,
                                  String documentation,
                                  String observationNotes) {
}
