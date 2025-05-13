package br.com.api_str_innovation.dto.checklist;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ChecklistRequestDTO(
        @NotBlank
        Boolean tire,
        @NotBlank
        String spareTire,
        @NotBlank
        String kilometersNumber,
        @NotBlank
        String fuelLevel,
        @NotBlank
        String oilLevel,
        @NotBlank
        String waterLevel,
        @NotBlank
        Boolean suspension,
        @NotBlank
        String brakes,
        @NotBlank
        Boolean glasses,
        @NotBlank
        Boolean windshieldWipers,
        @NotBlank
        Boolean rearview,
        @NotBlank
        Boolean headlight,
        @NotBlank
        Boolean taillight,
        @NotBlank
        Boolean frontIndicator,
        @NotBlank
        Boolean indicator,
        @NotBlank
        Boolean domeLight,
        @NotBlank
        Boolean licensePlateLight,
        @NotBlank
        Boolean licensePlate,
        @NotBlank
        String tirePressure,
        @NotBlank
        String jack,
        @NotBlank
        String toolbox,
        @NotBlank
        String documentation,
        @NotBlank
        String observationNotes,
        @NotBlank
        UUID employeeId,
        @NotBlank
        String employeeRole
) { }
