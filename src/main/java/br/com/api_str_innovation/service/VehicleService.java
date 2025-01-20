package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.checklist.ChecklistLogEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.exceptions.VehicleException;
import br.com.api_str_innovation.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistLogRepository checklistLogRepository;

    public VehicleEntity getById(UUID id) {
        return vehicleRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    private void existsPlateNumber(String licensePlateNumber) {
        if (vehicleRepository.findByLicensePlateNumber(licensePlateNumber).isPresent() ) {
            throw new VehicleException(String.format("A placa '%s' já está em uso.", licensePlateNumber));
        }
    }

    public List<VehicleResponseDTO> getAll() {
        return vehicleRepository
                .findAll()
                .stream()
                .map(VehicleResponseDTO::new)
                .toList();
    }

    public Integer count() {
        return vehicleRepository
                .findActiveVehicles()
                .toArray()
                .length;
    }

    @GetMapping(value = "/page")
    public Page<VehicleResponseDTO> getPaged(Pageable pageable) {
        return vehicleRepository
                .findActiveVehicles(pageable)
                .map(VehicleResponseDTO::new);
    }

    public void post(@Valid VehicleRequestDTO data) {
        existsPlateNumber(data.licensePlateNumber());
        vehicleRepository.save(new VehicleEntity(data));
    }

    @Transactional
    public void createChecklist(UUID vehicleId, @Valid ChecklistRequestDTO data) {
        VehicleEntity vehicle = getById(vehicleId);
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

    @Transactional
    public VehicleResponseDTO put(@PathVariable UUID id, @Valid VehicleRequestDTO data) {
        existsPlateNumber(data.licensePlateNumber());
        VehicleEntity vehicle = getById(id);

        vehicleRepository.update(
                id,
                data.licensePlateNumber() == null ? vehicle.getLicensePlateNumber() : data.licensePlateNumber(),
                data.sideNumber() == null ? vehicle.getSideNumber() : data.sideNumber(),
                data.model() == null ? vehicle.getModel() : data.model(),
                data.brand() == null ? vehicle.getBrand() : data.brand(),
                data.yearDt() == null ? vehicle.getYearDt() : data.yearDt(),
                data.status() == null ? vehicle.getStatus() : data.status().getStatus()
        );
        return new VehicleResponseDTO(vehicle);
    }

    public VehicleResponseDTO patch(@PathVariable UUID id, @Valid VehicleRequestDTO data) {
        VehicleEntity vehicle = this.getById(id);
        vehicle.setLicensePlateNumber(data.licensePlateNumber());
        vehicle.setSideNumber(data.sideNumber());
        vehicle.setModel(data.model());
        vehicle.setBrand(data.brand());
        vehicle.setYearDt(data.yearDt());
//        vehicle.setStatus(data.status());
        vehicleRepository.save(vehicle);
        return new VehicleResponseDTO(vehicle);
    }

    public void inactivate(UUID id) {
        VehicleEntity vehicle = getById(id);
        vehicle.setInactivatedDt(LocalDateTime.now());
        vehicleRepository.save(vehicle);
    }

    private static String changeStatusVehicle(ChecklistRequestDTO data) {
        if (data.tire().equals("missing")
        || data.tire().equals("damaged")
        || data.fuelLevel() <= 5
        || data.oilLevel() <= 4
        || data.waterLevel() <= 2
        || data.brakes().equals("damaged")
        || data.lights().equals("damaged")
        || data.glasses().equals("damaged")
        || data.documentation().equals("false")) {
            return "INACTIVE";
        } else {
            return "ACTIVE";
        }
    }

}

