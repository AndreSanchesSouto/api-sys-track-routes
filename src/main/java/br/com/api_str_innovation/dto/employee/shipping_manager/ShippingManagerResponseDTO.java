package br.com.api_str_innovation.dto.employee.shipping_manager;

import br.com.api_str_innovation.entities.employee.ShippingManagerEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ShippingManagerResponseDTO(UUID id,
                                         String name,
                                         String email,
                                         String login,
                                         String password,
                                         LocalDate createdDt,
                                         LocalDate inactivatedDt) {

    public ShippingManagerResponseDTO(ShippingManagerEntity shippingManager) {
        this(shippingManager.getId(),
                shippingManager.getName(),
                shippingManager.getEmail(),
                shippingManager.getLogin(),
                shippingManager.getPassword(),
                shippingManager.getCreatedDt(),
                shippingManager.getInactivatedDt());
    }
}
