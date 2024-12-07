package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.repository.ChecklistRepository;
import br.com.api_str_innovation.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    public List<ChecklistResponseDTO> getAll() {
        List<ChecklistResponseDTO> checklist = checklistRepository
                .findAll()
                .stream()
                .map(ChecklistResponseDTO::new)
                .toList();
        return checklist;
    }

    public Page<ChecklistResponseDTO> getPaged(Pageable pageable, @PathVariable UUID id) {
        vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        Page<ChecklistEntity> checklist = checklistRepository.findChecklistsByVehicleId(id, pageable);

        return checklist.map(ChecklistResponseDTO::new);
    }

    public ChecklistEntity getById(@PathVariable UUID id) {
        ChecklistEntity checklist = checklistRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checklist not found"));
        return checklist;
    }

    public void post(@PathVariable UUID id, @Valid ChecklistRequestDTO data) {
        ChecklistEntity checklist = new ChecklistEntity(data);

        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        checklist.setVehicle(List.of(vehicle));
        validateChecklistData(data);
        checklistRepository.save(checklist);
    }

    public ChecklistResponseDTO put(@PathVariable UUID id, ChecklistRequestDTO data) {
        ChecklistEntity checklist = this.getById(id);
        checklist.setTire(data.tire());
        checklist.setLicensePlateNumber(data.licensePlateNumber());
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
        checklist.setEditedDt(LocalDateTime.now());
        checklistRepository.save(checklist);
        return new ChecklistResponseDTO(checklist);
    }

    private void validateChecklistData(ChecklistRequestDTO data) {
        try {
            long kilometers = Long.parseLong(data.getKilometersNumber());
            if (!(kilometers >= 0)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kilometers number reported is negative");
            }
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kilometers number must be a number", ex);
        }
    }

}