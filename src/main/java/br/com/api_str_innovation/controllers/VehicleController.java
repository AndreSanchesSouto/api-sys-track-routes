package br.com.api_str_innovation.controllers;

import br.com.api_str_innovation.entities.vehicle.VehicleDomain;
import br.com.api_str_innovation.dtos.VehicleRecordDTO;
import br.com.api_str_innovation.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping()
    public ResponseEntity<VehicleDomain> createVehicle(@RequestBody @Valid VehicleRecordDTO vehicleRecordDto) {
        VehicleDomain vehicleDomain = new VehicleDomain();
        BeanUtils.copyProperties(vehicleRecordDto, vehicleDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleRepository.save(vehicleDomain));
    } // fazer try catch para se inputar dado nulo, ele mostra para o usuario
}