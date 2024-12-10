package br.com.api_str_innovation.entities.checklist;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "checklist")
@Entity
@Getter
@NoArgsConstructor
public class ChecklistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String tire;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String spareTire;

    @Setter
    @NotNull
    @Column(nullable = false)
    private Double kilometersNumber;

    @Setter
    @NotNull
    @Column(nullable = false)
    private Double fuelLevel;

    @Setter
    @NotNull
    @Column(nullable = false)
    private Double oilLevel;

    @Setter
    @NotNull
    @Column(nullable = false)
    private Double waterLevel;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String suspension;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String brakes;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String lights;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String glasses;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String windshieldWipers;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String jack;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String toolbox;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String documentation;

    @Setter
    @NotBlank
    private String observationNotes;

    @Column(nullable = false, updatable = false)
    private final LocalDate creationDt = LocalDate.now();

    @Setter
    private LocalDate editedDt;

    @Setter
    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    @Setter
    @Column(nullable = false)
    private UUID employeeId;

    @Setter
    @Column(nullable = false)
    private String employeeRole;

    public ChecklistEntity(ChecklistRequestDTO data) {
        this.tire = data.tire();
        this.spareTire = data.spareTire();
        this.kilometersNumber = data.kilometersNumber();
        this.fuelLevel = data.fuelLevel();
        this.oilLevel = data.oilLevel();
        this.waterLevel = data.waterLevel();
        this.suspension = data.suspension();
        this.brakes = data.brakes();
        this.lights = data.lights();
        this.glasses = data.glasses();
        this.windshieldWipers = data.windshieldWipers();
        this.jack = data.jack();
        this.toolbox = data.toolbox();
        this.documentation = data.documentation();
        this.observationNotes = data.observationNotes();
        this.employeeId = data.employeeId();
        this.employeeRole = data.employeeRole();
    }

}
