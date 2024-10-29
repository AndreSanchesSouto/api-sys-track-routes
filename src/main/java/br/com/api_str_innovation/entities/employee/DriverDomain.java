package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dtos.driver.DriverRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "driver")
@Entity
@Getter
@NoArgsConstructor
public class DriverDomain extends AbstractEmployeeDomain {
    @Column(nullable = false)
    private String status;

    public DriverDomain(DriverRequestDTO data) {
        super(data);
        this.status = data.status();
    }
}
