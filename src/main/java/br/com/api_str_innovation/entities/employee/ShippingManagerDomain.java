package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.driver.DriverRequestDTO;
import br.com.api_str_innovation.dto.shipping_manager.ShippingManagerRequestDTO;
import br.com.api_str_innovation.dto.shipping_manager.ShippingManagerResponseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "shipping_manager")
@Entity
@Getter
@NoArgsConstructor
public class ShippingManagerDomain extends AbstractEmployeeDomain {

    public ShippingManagerDomain(ShippingManagerRequestDTO data) {
    }
}
