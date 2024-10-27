package br.com.api_str_innovation.domain.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "shipping_manager")
@Entity
@Getter
@Setter
public class ShippingManagerDomain extends AbstractEmployeeDomain {
}
