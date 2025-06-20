package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.dashboard.DashboardDeliveryDTO;
import br.com.api_str_innovation.dto.dashboard.DashboardDriversDTO;
import br.com.api_str_innovation.dto.dashboard.DashboardVehiclesDTO;
import br.com.api_str_innovation.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/vehicles-status")
    public ResponseEntity<DashboardVehiclesDTO> getVehicleStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return this.service.getVehiclesStatus(generalManagerId);
    }

    @GetMapping("/driver-status")
    public ResponseEntity<DashboardDriversDTO> getDriverStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return this.service.getDriversStatus(generalManagerId);
    }

    @GetMapping("/delivery-status")
    public ResponseEntity<DashboardDeliveryDTO> getDeliveryStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return this.service.getDeliveryStatus(generalManagerId);
    }

}
