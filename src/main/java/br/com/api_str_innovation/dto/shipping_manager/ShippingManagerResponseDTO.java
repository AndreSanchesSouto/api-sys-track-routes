package br.com.api_str_innovation.dto.shipping_manager;

import br.com.api_str_innovation.entities.employee.ShippingManagerEntity;

import java.util.Date;
import java.util.UUID;

public record ShippingManagerResponseDTO(UUID id,
                                         String name,
                                         String email,
                                         String login,
                                         String password,
                                         Date creationDt,
                                         Date inactivationDt) {

    public ShippingManagerResponseDTO(ShippingManagerEntity shippingManager) {
        this(shippingManager.getId(),
                shippingManager.getName(),
                shippingManager.getEmail(),
                shippingManager.getLogin(),
                shippingManager.getPassword(),
                shippingManager.getCreationDt(),
                shippingManager.getInactivationDt());
    }
}
