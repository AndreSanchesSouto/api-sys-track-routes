package br.com.api_str_innovation.entities.vehicle;

import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "vehicle")
@Entity
@Getter
@NoArgsConstructor
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Column(nullable = false, unique = true)
    private String licensePlateNumber;

    @Setter
    @Column(nullable = false)
    private String sideNumber;

    @Setter
    @Column(nullable = false)
    private String model;

    @Setter
    @Column(nullable = false)
    private String brand;

    @Setter
    @Column(nullable = false)
    private String yearDt;

    @Setter
    @Column(nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdDt = LocalDateTime.now();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime inactivatedDt;

    @Getter
    @Setter
    @OneToOne(mappedBy = "vehicle")
    private ChecklistEntity checklist;

    public VehicleEntity(VehicleRequestDTO data) {
        this.licensePlateNumber = data.licensePlateNumber();
        this.sideNumber = data.sideNumber();
        this.model = data.model();
        this.brand = data.brand();
        this.yearDt = data.yearDt();
        this.status = getStatus() == null ? Status.WAITING.getStatus() : getStatus();
    }

}