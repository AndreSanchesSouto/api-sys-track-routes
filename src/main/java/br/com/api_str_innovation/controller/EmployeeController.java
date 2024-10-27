package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.domain.employee.DriverDomain;
import br.com.api_str_innovation.dtos.DriverRecordDto;
import br.com.api_str_innovation.repository.DriverRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class EmployeeController {

    @Autowired
    private DriverRepository driverRepository;

    @PostMapping("/createDriver")
    public ResponseEntity<DriverDomain> createDriver(@RequestBody @Valid DriverRecordDto driverRecordDto) {
        DriverDomain driverDomain = new DriverDomain();
        BeanUtils.copyProperties(driverRecordDto, driverDomain);
        driverDomain.setCreationDt(new Date());
        DriverDomain driverData = driverRepository.save(driverDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(driverData);
    }// fazer try catch para se inputar dado nulo, ele mostra para o usuario
}
