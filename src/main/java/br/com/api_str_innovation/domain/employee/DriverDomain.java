package br.com.api_str_innovation.domain.employee;

import br.com.api_str_innovation.domain.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "driver")
@Entity
@Getter
@Setter
public class DriverDomain extends AbstractEmployeeDomain {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus status;
}
