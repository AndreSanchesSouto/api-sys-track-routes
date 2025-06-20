package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.dashboard.DashboardDeliveryDTO;
import br.com.api_str_innovation.dto.dashboard.DashboardDriversDTO;
import br.com.api_str_innovation.dto.dashboard.DashboardVehiclesDTO;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryStatus;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.user.UserStatus;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryService deliveryService;

    public ResponseEntity<DashboardVehiclesDTO> getVehiclesStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        List<VehicleEntity> vehicleEntities = vehicleService.getAllByGeneralManagerId(generalManagerId);
        int waiting = 0;
        int active = 0;
        int unavailable = 0;
        int on_use = 0;
        int inactive = 0;
        for(VehicleEntity vehicle : vehicleEntities) {
            switch(VehicleStatus.valueOf(vehicle.getStatus().toUpperCase())) {
                case WAITING -> waiting++;
                case ACTIVE -> active++;
                case UNAVAILABLE -> unavailable++;
                case ON_USE -> on_use++;
                case INACTIVE -> inactive++;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new DashboardVehiclesDTO(waiting, active, unavailable, on_use, inactive));
    }

    public ResponseEntity<DashboardDriversDTO> getDriversStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        List<UserEntity> driverEntities = userService.getAllDriversByGeneralManagerId(generalManagerId);
        int active = 0;
        int unavailable = 0;
        int inactive = 0;
        for(UserEntity driver : driverEntities) {
            switch(UserStatus.valueOf(driver.getStatus().toUpperCase())) {
                case ACTIVE -> active++;
                case UNAVAILABLE -> unavailable++;
                case INACTIVE -> inactive++;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new DashboardDriversDTO(active, unavailable, inactive));
    }

    public ResponseEntity<DashboardDeliveryDTO> getDeliveryStatus(UUID generalManagerId) {
        List<DeliveryEntity> vehicleEntities = deliveryService.getAllByGeneralManagerId(generalManagerId);
        int waiting = 0;
        int active = 0;
        int onRoad = 0;
        int canceled = 0;
        int confirmed = 0;
        int comingBack = 0;

        for(DeliveryEntity delivery : vehicleEntities) {
            switch(DeliveryStatus.valueOf(delivery.getStatus().toUpperCase())) {
                case WAITING -> waiting++;
                case ACTIVE -> active++;
                case ON_ROAD -> onRoad++;
                case CANCELED -> canceled++;
                case CONFIRMED -> confirmed++;
                case COMING_BACK -> comingBack++;
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new DashboardDeliveryDTO(waiting, active, onRoad, canceled, confirmed, comingBack));
    }
}
