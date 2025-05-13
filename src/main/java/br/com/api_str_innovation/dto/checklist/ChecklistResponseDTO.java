package br.com.api_str_innovation.dto.checklist;

import br.com.api_str_innovation.entities.checklist.ChecklistEntity;

import java.time.LocalDate;
import java.util.UUID;

public record ChecklistResponseDTO(UUID id,
                                   Boolean tire,
                                   String spareTire,
                                   String kilometersNumber,
                                   String fuelLevel,
                                   String oilLevel,
                                   String waterLevel,
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
                                   Boolean licensePlate,
                                   String tirePressure,
                                   String jack,
                                   String toolbox,
                                   String documentation,
                                   String observationNotes,
                                   LocalDate creationDt,
                                   LocalDate editedDt) {

    public ChecklistResponseDTO(ChecklistEntity data) {
        this(
            data.getId(),
            data.getTire(),
            data.getSpareTire(),
            data.getKilometersNumber(),
            data.getFuelLevel(),
            data.getOilLevel(),
            data.getWaterLevel(),
            data.getSuspension(),
            data.getBrakes(),
            data.getGlasses(),
            data.getWindshieldWipers(),
            data.getRearview(),
            data.getHeadlight(),
            data.getTaillight(),
            data.getFrontIndicator(),
            data.getIndicator(),
            data.getDomeLight(),
            data.getLicensePlateLight(),
            data.getLicensePlate(),
            data.getTirePressure(),
            data.getJack(),
            data.getToolbox(),
            data.getDocumentation(),
            data.getObservationNotes(),
            data.getCreationDt(),
            data.getEditedDt()
        );
    }

}
