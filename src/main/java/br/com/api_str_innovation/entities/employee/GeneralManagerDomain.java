package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dtos.driver.DriverRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "general_manager")
@Entity
@Getter
@NoArgsConstructor
public class GeneralManagerDomain extends AbstractEmployeeDomain {
    public GeneralManagerDomain(DriverRequestDTO data) {
        super(data);
    }
}
