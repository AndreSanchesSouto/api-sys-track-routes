package br.com.api_str_innovation.dto.checklist;

import br.com.api_str_innovation.entities.checklist.ChecklistEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChecklistResponseDTO(UUID id,
                                   String tire,
                                   String licensePlateNumber,
                                   String spareTire,
                                   String kilometersNumber,
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
                                   String observationNotes,
                                   LocalDateTime creationDt,
                                   LocalDateTime editedDt) {

    public ChecklistResponseDTO(ChecklistEntity data) {
        this(
            data.getId(),
            data.getTire(),
            data.getLicensePlateNumber(),
            data.getSpareTire(),
            data.getKilometersNumber(),
            data.getFuelLevel(),
            data.getOilLevel(),
            data.getWaterLevel(),
            data.getSuspension(),
            data.getBrakes(),
            data.getLights(),
            data.getGlasses(),
            data.getWindshieldWipers(),
            data.getJack(),
            data.getToolbox(),
            data.getDocumentation(),
            data.getObservationNotes(),
            data.getCreationDt(),
            data.getEditedDt()
        );
    }

}
