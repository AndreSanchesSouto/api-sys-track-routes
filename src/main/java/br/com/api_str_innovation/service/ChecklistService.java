package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.dto.checklist.checklist_log.ChecklistLogRequestDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.checklist.ChecklistFieldOptions;
import br.com.api_str_innovation.entities.checklist.ChecklistLogEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleStatus;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.exceptions.DataAddressException;
import br.com.api_str_innovation.repository.ChecklistLogRepository;
import br.com.api_str_innovation.repository.ChecklistRepository;
import br.com.api_str_innovation.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ChecklistLogRepository checklistLogRepository;

    @Lazy
    @Autowired
    private DeliveryService deliveryService;

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
        return this.checklistRepository
                .findChecklistsByVehicleId(vehicleId)
                .map(ChecklistResponseDTO::new)
                .orElse(null);
    }

    public Optional<ChecklistEntity> findByVehicleId(UUID vehicleId) {
        return this.checklistRepository.findChecklistsByVehicleId(vehicleId);
    }

    public ChecklistEntity getById(@PathVariable UUID id) {
        return checklistRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checklist not found"));
    }

    @Transactional
    public void post(UUID vehicleId, @Valid ChecklistRequestDTO data) {
        VehicleEntity vehicle = vehicleRepository.getReferenceById(vehicleId);

        if (!containsCriticalStatus(data)) {
            vehicle.setStatus(
                vehicle.getStatus().equals(VehicleStatus.ON_USE.getStatus())
                    ? VehicleStatus.ON_USE.getStatus()
                    : VehicleStatus.ACTIVE.getStatus()
            );
        } else {
            vehicle.setStatus(VehicleStatus.INACTIVE.getStatus());
        }
        
        vehicleRepository.updateStatusVehicle(vehicle.getStatus(), vehicleId);

        ChecklistEntity checklist = new ChecklistEntity(data);
        checklist.setVehicle(vehicle);
        checklistRepository.save(checklist);

        ChecklistLogEntity checklistLog = new ChecklistLogEntity(data);
        checklistLog.setVehicleId(vehicleId);
        checklistLog.setVehicleStatus(vehicle.getStatus());
        checklistLogRepository.save(checklistLog);
    }

    private boolean containsCriticalStatus(ChecklistRequestDTO data) {
        return isHeadlightTrue(data)
                || isTaillightTrue(data)
                || isFrontIndicatorTrue(data)
                || isIndicatorTrue(data)
                || isDomeLightTrue(data)
                || isLicensePlateLightTrue(data)
                || isTireTrue(data)
                || isGlassesTrue(data)
                || isRearviewTrue(data)
                || islicensePlateTrue(data)
                || isWindshieldWipersTrue(data)
                || isSuspensionTrue(data)
                || isJackMissingOrDamaged(data)
                || isBrakesMissingOrDamaged(data)
                || isSpareTireMissingOrDamaged(data)
                || isTirePressureMissingOrDamaged(data)
                || isDocumentationInvalid(data)

                || isFuelLevelTooLow(data)
                || isOilLevelTooLow(data)
                || isWaterLevelTooLow(data);
    }

    private boolean isHeadlightTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.headlight()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isTaillightTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.taillight()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isFrontIndicatorTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.frontIndicator()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isIndicatorTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.indicator()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isDomeLightTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.domeLight()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isLicensePlateLightTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.licensePlateLight()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isTireTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.tire()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isGlassesTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.glasses()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isRearviewTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.rearview()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean islicensePlateTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.licensePlate()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isWindshieldWipersTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.windshieldWipers()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isSuspensionTrue(ChecklistRequestDTO data) {
        return Boolean.toString(data.suspension()).equals(ChecklistFieldOptions.STATUS_TRUE.getField());
    }

    private boolean isJackMissingOrDamaged(ChecklistRequestDTO data) {
        return data.jack().equals(ChecklistFieldOptions.STATUS_MISSING.getField())
            || data.jack().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isBrakesMissingOrDamaged(ChecklistRequestDTO data) {
        return data.brakes().equals(ChecklistFieldOptions.STATUS_MISSING.getField())
            || data.brakes().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isSpareTireMissingOrDamaged(ChecklistRequestDTO data) {
        return data.spareTire().equals(ChecklistFieldOptions.STATUS_MISSING.getField())
            || data.spareTire().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isTirePressureMissingOrDamaged(ChecklistRequestDTO data) {
        return data.tirePressure().equals(ChecklistFieldOptions.STATUS_MISSING.getField())
            || data.tirePressure().equals(ChecklistFieldOptions.STATUS_DAMAGED.getField());
    }

    private boolean isDocumentationInvalid(ChecklistRequestDTO data) {
        return data.documentation().equals(ChecklistFieldOptions.STATUS_MISSING.getField())
            || data.documentation().equals(ChecklistFieldOptions.STATUS_INVALID.getField());
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
        checklist.setGlasses(data.glasses());
        checklist.setWindshieldWipers(data.windshieldWipers());
        checklist.setRearview(data.rearview());
        checklist.setHeadlight(data.headlight());
        checklist.setTaillight(data.taillight());
        checklist.setFrontIndicator(data.frontIndicator());
        checklist.setIndicator(data.indicator());
        checklist.setDomeLight(data.domeLight());
        checklist.setLicensePlateLight(data.licensePlateLight());
        checklist.setTirePressure(data.tirePressure());
        checklist.setToolbox(data.toolbox());
        checklist.setDocumentation(data.documentation());
        checklist.setDocumentation(data.documentation());
        checklist.setEditedDt(LocalDateTime.now());
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
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível deletar o checklist")
        );

        if (!vehicle.getStatus().equals(VehicleStatus.ON_USE.getStatus())) {
            vehicleRepository.updateStatusVehicle(VehicleStatus.WAITING.getStatus(), vehicle.getId());
        }

        if(deliveryService.findActiveByChecklistId(id)!=null) {
            throw new DataAddressException("Veículo em uso");
        }

        checklistRepository.deleteById(id);
    }

    public List<Object[]> countKmDriven(UUID vehicleId, PeriodTimeRequestDTO data) {
        return checklistLogRepository.countKmDriven(vehicleId, data.from(), data.to());
    }

    public List<Object[]> getDetailedKmSegments(UUID vehicleId, PeriodTimeRequestDTO data) {
        return checklistLogRepository.getDetailedKmSegments(vehicleId, data.from(), data.to());
    }

    public List<Object[]> countChecklistStatusVehicleActive(ChecklistLogRequestDTO data) {
        return checklistLogRepository.countChecklistStatusVehicleActive(data.startDate(), data.endDate());
    }

    public List<Object[]> countChecklistStatusVehicleInactive(ChecklistLogRequestDTO data) {
        return checklistLogRepository.countChecklistStatusVehicleInactive(data.startDate(), data.endDate());
    }
}