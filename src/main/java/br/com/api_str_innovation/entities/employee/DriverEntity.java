package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.driver.DriverRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "driver")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DriverEntity extends AbstractEmployeeEntity {

    private String status;

    public DriverEntity(DriverRequestDTO data) {
        super(data);
        this.status = data.getStatus() == null ? "WAITING" : data.getStatus();
    }
}
