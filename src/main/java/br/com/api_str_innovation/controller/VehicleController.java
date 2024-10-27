package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.domain.vehicle.VehicleDomain;
import br.com.api_str_innovation.dtos.VehicleRecordDto;
import br.com.api_str_innovation.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping("/createVehicle")
    public ResponseEntity<VehicleDomain> createVehicle(@RequestBody @Valid VehicleRecordDto vehicleRecordDto) {
        VehicleDomain vehicleDomain = new VehicleDomain();
        BeanUtils.copyProperties(vehicleRecordDto, vehicleDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleRepository.save(vehicleDomain));
    } // fazer try catch para se inputar dado nulo, ele mostra para o usuario
}