package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.checklist.ChecklistLogEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
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
import org.springframework.web.bind.annotation.RequestBody;
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

    public List<VehicleResponseDTO> getAll() {
        List<VehicleResponseDTO> vehicle = vehicleRepository
                .findAll()
                .stream()
                .map(VehicleResponseDTO::new)
                .toList();
        return vehicle;
    }

    public Integer count() {
        Integer count = vehicleRepository
                .findActiveVehicles()
                .toArray()
                .length;
        return count;
    }

    @GetMapping(value = "/page")
    public Page<VehicleResponseDTO> getPaged(Pageable pageable) {
        Page<VehicleResponseDTO> vehicle = vehicleRepository
                .findActiveVehicles(pageable)
                .map(VehicleResponseDTO::new);
        return vehicle;
    }

    public VehicleEntity getById(UUID id) {
        VehicleEntity vehicle = vehicleRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        return vehicle;
    }

    public void post(@Valid VehicleRequestDTO data) {
        VehicleEntity vehicleData = new VehicleEntity(data);
        vehicleRepository.save(vehicleData);
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
        checklistLogRepository.save(checklistLog);
    }

    public VehicleResponseDTO put(@PathVariable UUID id, @Valid VehicleRequestDTO data) {
        VehicleEntity vehicle = this.getById(id);
        vehicle.setLicensePlateNumber(data.licensePlateNumber());
        vehicle.setSideNumber(data.sideNumber());
        vehicle.setModel(data.model());
        vehicle.setBrand(data.brand());
        vehicle.setYearDt(data.yearDt());
        vehicle.setStatus(data.status());
        vehicleRepository.save(vehicle);
        return new VehicleResponseDTO(vehicle);
    }

    public void inactivate(UUID id) {
        VehicleEntity vehicle = getById(id);
        vehicle.setInactivatedDt(LocalDateTime.now());
        vehicleRepository.save(vehicle);
    }

    private static String changeStatusVehicle(ChecklistRequestDTO data) {
        if (data.tire().equals("Faltando")
        || data.tire().equals("Danificados")
        || data.fuelLevel() <= 5
        || data.oilLevel() <= 4
        || data.waterLevel() <= 2
        || data.brakes().equals("Danificados")
        || data.lights().equals("Danificados")
        || data.glasses().equals("Danificados")
        || data.documentation().equals("Não")) {
            return "INACTIVE";
        } else {
            return "ACTIVE";
        }
    }

}

