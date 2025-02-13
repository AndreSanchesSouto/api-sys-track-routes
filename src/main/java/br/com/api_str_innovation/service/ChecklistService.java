package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.dto.checklist.checklist_log.ChecklistLogRequestDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.checklist.ChecklistFieldOptions;
import br.com.api_str_innovation.entities.checklist.ChecklistLogEntity;
import br.com.api_str_innovation.entities.vehicle.Status;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.repository.ChecklistLogRepository;
import br.com.api_str_innovation.repository.ChecklistRepository;
import br.com.api_str_innovation.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired ChecklistLogRepository checklistLogRepository;

    public List<ChecklistResponseDTO> getAll() {
        return checklistRepository
                .findAll()
                .stream()
                .map(ChecklistResponseDTO::new)
                .toList();
    }

//    public Page<ChecklistResponseDTO> getPaged(Pageable pageable, @PathVariable UUID id) {
//        vehicleRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
//
//        Page<ChecklistEntity> checklist = checklistRepository.findChecklistsByVehicleId(id, pageable);
//
//        return checklist.map(ChecklistResponseDTO::new);
//    }

    public ChecklistResponseDTO getByVehicleId(UUID vehicleId) {
        ChecklistEntity checklist =  this.checklistRepository
                .findChecklistsByVehicleId(vehicleId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checklist not found")
                );

        return new ChecklistResponseDTO(checklist);
    }

    public ChecklistEntity getById(@PathVariable UUID id) {
        return checklistRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checklist not found"));
    }

    @Transactional
    public void post(UUID vehicleId, @Valid ChecklistRequestDTO data) {
        VehicleEntity vehicle = vehicleRepository.getReferenceById(vehicleId);
        vehicle.setStatus(changeStatusVehicle(data));
        vehicleRepository.updateStatusVehicle(vehicle.getStatus(), vehicleId);

        ChecklistEntity checklist = new ChecklistEntity(data);
        checklist.setVehicle(vehicle);
        checklistRepository.save(checklist);

        ChecklistLogEntity checklistLog = new ChecklistLogEntity(data);
        checklistLog.setVehicleId(vehicleId);
        checklistLog.setVehicleStatus(vehicle.getStatus());
        checklistLogRepository.save(checklistLog);
    }

    private String changeStatusVehicle(ChecklistRequestDTO data) {
        return containsCriticalStatus(data) ? Status.INACTIVE.getStatus() : Status.ACTIVE.getStatus();
    }

    private boolean containsCriticalStatus(ChecklistRequestDTO data) {
        return isTireMissingOrDamaged(data)
                || isBrakesDamaged(data)
                || isLightsDamaged(data)
                || isGlassesDamaged(data)
                || isDocumentationInvalid(data)
                || isFuelLevelTooLow(data)
                || isOilLevelTooLow(data)
                || isWaterLevelTooLow(data);
    }

    private boolean isTireMissingOrDamaged(ChecklistRequestDTO data) {
        return data.tire().equals(ChecklistFieldOptions.STATUS_MISSING.getField()) ||
                data.tire().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isBrakesDamaged(ChecklistRequestDTO data) {
        return data.brakes().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isLightsDamaged(ChecklistRequestDTO data) {
        return data.lights().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isGlassesDamaged(ChecklistRequestDTO data) {
        return data.glasses().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isDocumentationInvalid(ChecklistRequestDTO data) {
        return data.documentation().equals(ChecklistFieldOptions.STATUS_FALSE.getField());
    }

    private boolean isFuelLevelTooLow(ChecklistRequestDTO data) {
        return Double.parseDouble(data.fuelLevel()) <= Double.parseDouble(ChecklistFieldOptions.MINIMUM_FUEL_LEVEL.getField());
    }

    private boolean isOilLevelTooLow(ChecklistRequestDTO data) {
        return Double.parseDouble(data.oilLevel()) <= Double.parseDouble(ChecklistFieldOptions.MINIMUM_OIL_LEVEL.getField());
    }

    private boolean isWaterLevelTooLow(ChecklistRequestDTO data) {
        return Double.parseDouble(data.waterLevel()) <= Double.parseDouble(ChecklistFieldOptions.MINIMUM_WATER_LEVEL.getField());
    }


    public ChecklistResponseDTO put(@PathVariable UUID id, ChecklistRequestDTO data) {
        ChecklistEntity checklist = this.getById(id);

        checklist.setTire(data.tire());
        checklist.setSpareTire(data.spareTire());
        checklist.setKilometersNumber(data.kilometersNumber());
        checklist.setFuelLevel(data.fuelLevel());
        checklist.setOilLevel(data.oilLevel());
        checklist.setWaterLevel(data.waterLevel());
        checklist.setSuspension(data.suspension());
        checklist.setBrakes(data.brakes());
        checklist.setLights(data.lights());
        checklist.setGlasses(data.glasses());
        checklist.setWindshieldWipers(data.windshieldWipers());
        checklist.setToolbox(data.toolbox());
        checklist.setDocumentation(data.documentation());
        checklist.setDocumentation(data.documentation());
        checklist.setEditedDt(LocalDate.now());
        checklistRepository.save(checklist);

        return new ChecklistResponseDTO(checklist);
    }

//    private void validateChecklistData(ChecklistRequestDTO data) {
//        try {
//            long kilometers = Long.parseLong(data.getKilometersNumber());
//            if (!(kilometers >= 0)) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kilometers number reported is negative");
//            }
//        } catch (NumberFormatException ex) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kilometers number must be a number", ex);
//        }
//    }

    @Transactional
    public void deleteById(UUID id) {
        VehicleEntity vehicle = vehicleRepository.findVehicleFromChecklistId(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "aaaa")
        );
        checklistRepository.deleteById(vehicle.getId());
        vehicleRepository.updateStatusVehicle(Status.WAITING.getStatus(), vehicle.getId());
    }

    public List<Object[]> countKmDriven(UUID vehicleId, ChecklistLogRequestDTO data) {
        return checklistLogRepository.countKmDriven(vehicleId, data.startDate(), data.endDate());
    }

    public List<Object[]> countChecklistStatusVehicleActive(ChecklistLogRequestDTO data) {
        return checklistLogRepository.countChecklistStatusVehicleActive(data.startDate(), data.endDate());
    }

    public List<Object[]> countChecklistStatusVehicleInactive(ChecklistLogRequestDTO data) {
        return checklistLogRepository.countChecklistStatusVehicleInactive(data.startDate(), data.endDate());
    }
}