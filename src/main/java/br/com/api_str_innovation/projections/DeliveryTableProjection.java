package br.com.api_str_innovation.projections;

import java.time.LocalDate;

public interface DeliveryTableProjection {
    String getDeliveryRequest();
    String getVehiclePlate();
    String getDriverLogin();
    String getItems();
    String getStatus();
    LocalDate getDate();
}
