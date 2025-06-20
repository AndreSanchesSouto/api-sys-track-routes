package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleStatus;
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
    private VehicleRepository repository;

    public VehicleResponseDTO getById(UUID id) {
        return repository
                .findById(id)
                .map(VehicleResponseDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
    }

    public VehicleEntity findById(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
    }

    private void existsPlateNumber(String licensePlateNumber) {
        if (repository.findByLicensePlateNumber(licensePlateNumber).isPresent() ) {
            throw new VehicleException(String.format("A placa '%s' já está em uso.", licensePlateNumber));
        }
    }

    public List<VehicleResponseDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(VehicleResponseDTO::new)
                .toList();
    }

    public List<VehicleResponseDTO> findAvailableVehicles(UUID generalManagerId) {
        return repository
                .findAvailableVehicles(generalManagerId)
                .stream()
                .map(VehicleResponseDTO::new)
                .toList();
    }

    public Integer count(UUID generalManagerId) {
        return repository
                .findActiveVehicles(generalManagerId)
                .toArray()
                .length;
    }

    @GetMapping(value = "/page")
    public Page<VehicleResponseDTO> getPaged(Pageable pageable, UUID generalManagerId) {
        return repository
                .findActiveVehicles(pageable, generalManagerId)
                .map(VehicleResponseDTO::new);
    }

    public Page<VehicleResponseDTO> getByStatus(String status, Pageable pageable, UUID generalManagerId) {

            return repository
                    .findByStatusAndGeneralManagerId(status.toLowerCase(), generalManagerId, pageable)
                    .map(VehicleResponseDTO::new);
    }

    @GetMapping(value = "/search/license-plate")
    public Page<VehicleResponseDTO> getSearched(Pageable pageable, String licensePlateNumber, UUID generalManagerId) {
        return repository
                .findSearchedVehicles(pageable, licensePlateNumber, generalManagerId)
                .map(VehicleResponseDTO::new);
    }

    public void post(@Valid VehicleRequestDTO data, UUID generalManagerId) {
        existsPlateNumber(data.licensePlateNumber());
        repository.save(new VehicleEntity(data, generalManagerId));
    }

    @Transactional
    public VehicleResponseDTO put(@PathVariable UUID id, @Valid VehicleRequestDTO data) {
        existsPlateNumber(data.licensePlateNumber());
        VehicleEntity vehicle = findById(id);

        vehicle.setLicensePlateNumber(data.licensePlateNumber());
        vehicle.setSideNumber(data.sideNumber());
        vehicle.setModel(data.model());
        vehicle.setBrand(data.brand());
        vehicle.setYearDt(data.yearDt());
        repository.save(vehicle);

        return new VehicleResponseDTO(vehicle);
    }

    @Transactional
    public VehicleResponseDTO patch(@PathVariable UUID id, @Valid VehicleRequestDTO data) {
        VehicleEntity vehicle = findById(id);

        vehicle.setLicensePlateNumber(data.licensePlateNumber());
        vehicle.setSideNumber(data.sideNumber());
        vehicle.setModel(data.model());
        vehicle.setBrand(data.brand());
        vehicle.setYearDt(data.yearDt());
        repository.save(vehicle);

        return new VehicleResponseDTO(vehicle);
    }

    @Transactional
    public void inactivate(UUID id) {
        VehicleEntity vehicle = findById(id);

        if(vehicle.getInactivatedDt() != null) {
            throw new VehicleException("Veículo já inativo");
        }

        vehicle.setInactivatedDt(LocalDateTime.now());
        repository.save(vehicle);
    }

    @Transactional
    public void patchStatus(UUID id, VehicleStatus status) {
        VehicleEntity vehicle = findById(id);
        vehicle.setStatus(status.getStatus());
        repository.save(vehicle);
    }

    public List<VehicleEntity> getAllByGeneralManagerId(UUID generalManagerId) {
        return this.repository.getAllByGeneralManagerId(generalManagerId);
    }
}

