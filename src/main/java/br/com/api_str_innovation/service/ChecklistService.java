package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistReportDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.dto.checklist.checklist_log.ChecklistLogRequestDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.checklist.ChecklistFieldOptions;
import br.com.api_str_innovation.entities.checklist.ChecklistLogEntity;
import br.com.api_str_innovation.entities.checklist.LogsUserChecklistEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleStatus;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.exceptions.DataAddressException;
import br.com.api_str_innovation.repository.ChecklistLogRepository;
import br.com.api_str_innovation.repository.ChecklistRepository;
import br.com.api_str_innovation.repository.LogsUserChecklistRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChecklistService {

    private static final int CREATED_DT_INDEX = 0;
    private static final int OBSERVATION_NOTES_INDEX = 1;
    private static final int HEADLIGHT_INDEX = 2;
    private static final int TAILLIGHT_INDEX = 3;
    private static final int FRONT_INDICATOR_INDEX = 4;
    private static final int INDICATOR_INDEX = 5;
    private static final int DOME_LIGHT_INDEX = 6;
    private static final int LICENSE_PLATE_LIGHT_INDEX = 7;
    private static final int TIRE_INDEX = 8;
    private static final int GLASSES_INDEX = 9;
    private static final int REARVIEW_INDEX = 10;
    private static final int LICENSE_PLATE_INDEX = 11;
    private static final int WINDSHIELD_WIPERS_INDEX = 12;
    private static final int SUSPENSION_INDEX = 13;
    private static final int JACK_INDEX = 14;
    private static final int BRAKES_INDEX = 15;
    private static final int SPARE_TIRE_INDEX = 16;
    private static final int TIRE_PRESSURE_INDEX = 17;
    private static final int DOCUMENTATION_INDEX = 18;
    private static final int FUEL_LEVEL_INDEX = 19;
    private static final int OIL_LEVEL_INDEX = 20;
    private static final int WATER_LEVEL_INDEX = 21;
    private static final int LICENSE_PLATE_NUMBER_INDEX = 22;
    private static final int DRIVER_NAME_INDEX = 23;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ChecklistLogRepository checklistLogRepository;

    @Autowired
    private LogsUserChecklistRepository logsUserChecklistRepository;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private DeliveryService deliveryService;

    public List<ChecklistResponseDTO> getAll(UUID generalManagerId) {
        return checklistRepository
            .findAll()
            .stream()
            .filter(checklist -> checklist.getVehicle() != null && checklist.getVehicle().getGeneralManagerId() != null && checklist.getVehicle().getGeneralManagerId().equals(generalManagerId))
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
    public void post(UUID vehicleId, @Valid ChecklistRequestDTO data, UUID userId) {
        ChecklistLogEntity lastLog = checklistLogRepository
            .findFirstByVehicleIdOrderByCreatedDtDesc(vehicleId)
            .orElse(null);

        if (lastLog != null) {
            try {
                double lastKm = Double.parseDouble(lastLog.getKilometersNumber());
                double newKm = Double.parseDouble(data.kilometersNumber());
                if (newKm < lastKm) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quilometragem do novo checklist não pode ser menor que a última registrada: " + lastKm + " km");
                }
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quilometragem inválida no checklist atual ou anterior.");
            }
        }

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

        // Buscar informações do usuário e criar log
        UserEntity user = userService.findById(userId);
        LogsUserChecklistEntity userLog = new LogsUserChecklistEntity(userId, user.getName(), "CRIADO", checklist.getId(), vehicleId);
        logsUserChecklistRepository.save(userLog);
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
    public void deleteById(UUID id, UUID userId) {
        VehicleEntity vehicle = vehicleRepository.findVehicleFromChecklistId(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível deletar o checklist")
        );

        if (!vehicle.getStatus().equals(VehicleStatus.ON_USE.getStatus())) {
            vehicleRepository.updateStatusVehicle(VehicleStatus.WAITING.getStatus(), vehicle.getId());
        }

        if(deliveryService.findActiveByChecklistId(id)!=null) {
            throw new DataAddressException("Veículo em uso");
        }

        // Buscar informações do usuário e criar log antes de deletar
        UserEntity user = userService.findById(userId);
        LogsUserChecklistEntity userLog = new LogsUserChecklistEntity(userId, user.getName(), "APAGADO", id, vehicle.getId());
        logsUserChecklistRepository.save(userLog);

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

    public List<ChecklistReportDTO> getChecklistReport(UUID vehicleId, UUID generalManagerId, PeriodTimeRequestDTO data) {
        List<Object[]> results = checklistLogRepository.findReportData(vehicleId, generalManagerId, data.from(), data.to());
        List<ChecklistReportDTO> report = new ArrayList<>();

        for (Object[] row : results) {
            String licensePlate = (String) row[LICENSE_PLATE_NUMBER_INDEX];
            String driverName = (String) row[DRIVER_NAME_INDEX];
            LocalDateTime createdDt = parseLocalDateTime(row[CREATED_DT_INDEX]);
            String observationNotes = (String) row[OBSERVATION_NOTES_INDEX];

            List<String> problems = new ArrayList<>();

            if (parseBoolean(row[HEADLIGHT_INDEX])) problems.add("Farol com problema");
            if (parseBoolean(row[TAILLIGHT_INDEX])) problems.add("Lanterna com problema");
            if (parseBoolean(row[FRONT_INDICATOR_INDEX])) problems.add("Indicador dianteiro com problema");
            if (parseBoolean(row[INDICATOR_INDEX])) problems.add("Indicador traseiro com problema");
            if (parseBoolean(row[DOME_LIGHT_INDEX])) problems.add("Luz de teto com problema");
            if (parseBoolean(row[LICENSE_PLATE_LIGHT_INDEX])) problems.add("Luz da placa com problema");
            if (parseBoolean(row[TIRE_INDEX])) problems.add("Pneu com problema");
            if (parseBoolean(row[GLASSES_INDEX])) problems.add("Vidros com problema");
            if (parseBoolean(row[REARVIEW_INDEX])) problems.add("Retrovisor com problema");
            if (parseBoolean(row[LICENSE_PLATE_INDEX])) problems.add("Placa com problema");
            if (parseBoolean(row[WINDSHIELD_WIPERS_INDEX])) problems.add("Limpador de para-brisa com problema");
            if (parseBoolean(row[SUSPENSION_INDEX])) problems.add("Suspensão com problema");

            String jackValue = parseString(row[JACK_INDEX]);
            String brakesValue = parseString(row[BRAKES_INDEX]);
            String spareTireValue = parseString(row[SPARE_TIRE_INDEX]);
            String tirePressureValue = parseString(row[TIRE_PRESSURE_INDEX]);
            String documentationValue = parseString(row[DOCUMENTATION_INDEX]);
            
            if (isProblem(jackValue)) problems.add("Macaco " + getProblemDescription(jackValue));
            if (isProblem(brakesValue)) problems.add("Freios " + getProblemDescription(brakesValue));
            if (isProblem(spareTireValue)) problems.add("Estepe " + getProblemDescription(spareTireValue));
            if (isProblem(tirePressureValue)) problems.add("Pressão dos pneus " + getProblemDescription(tirePressureValue));
            if (isProblem(documentationValue)) problems.add("Documentação " + getProblemDescription(documentationValue));

            String fuelLevelValue = parseString(row[FUEL_LEVEL_INDEX]);
            String oilLevelValue = parseString(row[OIL_LEVEL_INDEX]);
            String waterLevelValue = parseString(row[WATER_LEVEL_INDEX]);
            
            if (isLevelTooLow(fuelLevelValue, ChecklistFieldOptions.MINIMUM_FUEL_LEVEL.getField())) {
                problems.add("Combustível baixo: " + fuelLevelValue + "L");
            }
            if (isLevelTooLow(oilLevelValue, ChecklistFieldOptions.MINIMUM_OIL_LEVEL.getField())) {
                problems.add("Óleo baixo: " + oilLevelValue + "L");
            }
            if (isLevelTooLow(waterLevelValue, ChecklistFieldOptions.MINIMUM_WATER_LEVEL.getField())) {
                problems.add("Água baixa: " + waterLevelValue + "L");
            }

            if (!problems.isEmpty()) {
                report.add(new ChecklistReportDTO(
                    createdDt,
                    licensePlate,
                    driverName,
                    problems,
                    observationNotes
                ));
            }
        }
        return report;
    }

    private boolean parseBoolean(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof String) return Boolean.parseBoolean((String) value);
        if (value instanceof Number) return ((Number) value).intValue() != 0;
        return false;
    }

    private String parseString(Object value) {
        if (value == null) return null;
        if (value instanceof String) return (String) value;
        return value.toString();
    }

    private LocalDateTime parseLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        
        try {
            if (value instanceof java.sql.Timestamp) {
                return ((java.sql.Timestamp) value).toLocalDateTime();
            }
            
            if (value instanceof java.sql.Date) {
                return ((java.sql.Date) value).toLocalDate().atStartOfDay();
            }
            
            if (value instanceof java.util.Date) {
                return ((java.util.Date) value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
            }
            
            if (value instanceof LocalDateTime) {
                return (LocalDateTime) value;
            }
            
            if (value instanceof String) {
                String strValue = (String) value;
                if (strValue.trim().isEmpty()) {
                    return null;
                }
                return LocalDateTime.parse(strValue);
            }
            
            if (value instanceof Number) {
                Number numValue = (Number) value;
                long timestamp = numValue.longValue();
                if (timestamp > 0) {
                    return java.time.Instant.ofEpochMilli(timestamp)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime();
                }
            }
            
            return null;
            
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isProblem(String value) {
        if (value == null) return false;
        String v = value.toLowerCase();
        return v.equals("missing") || v.equals("damaged") || v.equals("invalid");
    }

    private String getProblemDescription(String value) {
        if (value == null) return null;
        String v = value.toLowerCase();
        if (v.equals("missing")) return "faltando";
        if (v.equals("damaged")) return "com problema";
        if (v.equals("invalid")) return "vencida";
        return value;
    }

    private boolean isLevelTooLow(String value, String minimumLevel) {
        if (value == null || minimumLevel == null) return false;
        try {
            double level = Double.parseDouble(value);
            double minLevel = Double.parseDouble(minimumLevel);
            return level <= minLevel;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}