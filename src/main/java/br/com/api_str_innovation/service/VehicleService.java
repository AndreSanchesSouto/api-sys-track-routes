package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.repository.*;
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
    private DriverRepository driverRepository;

    @Autowired
    ShippingManagerRepository shippingManagerRepository;

    @Autowired
    GeneralManagerRepository generalManagerRepository;

    @Autowired
    private ChecklistRepository checklistRepository;

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

    public void createChecklist(@PathVariable UUID vehicleId, @Valid ChecklistRequestDTO data) {
        ChecklistEntity checklist = new ChecklistEntity(data);
        getById(vehicleId);
        checklistRepository.save(checklist);
    }

    public VehicleResponseDTO put(@PathVariable UUID id, @RequestBody VehicleRequestDTO data) {
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

}