package br.com.api_str_innovation.dto.checklist;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record ChecklistRequestDTO(@NotBlank String tire,
                                  @NotBlank String licensePlateNumber,
                                  @NotBlank String spareTire,
                                  @NotBlank @Getter String kilometersNumber,
                                  @NotBlank String fuelLevel,
                                  @NotBlank String oilLevel,
                                  @NotBlank String waterLevel,
                                  @NotBlank String suspension,
                                  @NotBlank String brakes,
                                  @NotBlank String lights,
                                  @NotBlank String glasses,
                                  @NotBlank String windshieldWipers,
                                  @NotBlank String jack,
                                  @NotBlank String toolbox,
                                  @NotBlank String documentation,
                                  String observationNotes) {
}
