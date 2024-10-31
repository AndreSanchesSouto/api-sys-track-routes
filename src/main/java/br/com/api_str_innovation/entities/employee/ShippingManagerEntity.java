package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.EmployeeRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "shipping_manager")
@Entity
@Getter
@NoArgsConstructor
public class ShippingManagerEntity extends AbstractEmployeeEntity {
    public ShippingManagerEntity(EmployeeRequest data) {
        super(data);
    }
}
