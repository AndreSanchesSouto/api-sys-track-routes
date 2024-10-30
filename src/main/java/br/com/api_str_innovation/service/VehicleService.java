package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleDomain;
import br.com.api_str_innovation.repository.VehicleRepository;
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

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public List<VehicleResponseDTO> getAll() {
        List<VehicleResponseDTO> vehicle = repository
                .findAll()
                .stream()
                .map(VehicleResponseDTO::new)
                .toList();
        return vehicle;
    }

    @GetMapping(value = "/page")
    public Page<VehicleResponseDTO> getPaged(Pageable pageable) {
        Page<VehicleResponseDTO> vehicle = repository
                .findAll(pageable)
                .map(VehicleResponseDTO::new);
        return vehicle;
    }

    public VehicleDomain getById(UUID id) {
        VehicleDomain vehicle = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle not found"));
        return vehicle;
    }

    public void post(@Valid VehicleRequestDTO data) {
        System.out.println(data);
        VehicleDomain vehicleData = new VehicleDomain(data);
        repository.save(vehicleData);
    }

    // Exist an error when don`t fill in all fields, it catches null. Front-end resolve that?
    public VehicleResponseDTO put(@PathVariable UUID id, @RequestBody VehicleRequestDTO data) {
        VehicleDomain vehicle = this.getById(id);
        vehicle.setLicensePlateNumber(data.licensePlateNumber());
        vehicle.setSideNumber(data.sideNumber());
        vehicle.setModel(data.model());
        vehicle.setBrand(data.brand());
        vehicle.setStatus(data.status());
        repository.save(vehicle);
        return new VehicleResponseDTO(vehicle);
    }

    public void inactivate(UUID id) {
        VehicleDomain vehicle = getById(id);
        vehicle.setStatus("INACTIVE");
        repository.save(vehicle);
    }

}
