package br.com.api_str_innovation.projections;

import java.time.LocalDate;
import java.util.UUID;

public interface DeliveryTableProjection {
    UUID getId();
    String getDeliveryRequest();
    String getVehiclePlate();
    String getDriverLogin();
    String getItems();
    String getStatus();
    LocalDate getDate();
}
