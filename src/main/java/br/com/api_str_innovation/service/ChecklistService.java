package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.repository.ChecklistRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistRepository repository;

    public List<ChecklistResponseDTO> getAll() {
        List<ChecklistResponseDTO> checklist = repository
                .findAll()
                .stream()
                .map(ChecklistResponseDTO::new)
                .toList();
        return checklist;
    }

    @GetMapping(value = "/page")
    public Page<ChecklistResponseDTO> getPaged(Pageable pageable) {
        Page<ChecklistResponseDTO> client = repository
                .findAll(pageable)
                .map(ChecklistResponseDTO::new);
        return client;
    }

    public ChecklistEntity getById(UUID id) {
        ChecklistEntity client = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checklist not found"));
        return client;
    }

    public void post(@Valid ChecklistRequestDTO data) {
        validateChecklistData(data);
        ChecklistEntity clientData = new ChecklistEntity(data);
        repository.save(clientData);
    }

    public ChecklistResponseDTO put(UUID id, ChecklistRequestDTO data) {
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
        repository.save(checklist);
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