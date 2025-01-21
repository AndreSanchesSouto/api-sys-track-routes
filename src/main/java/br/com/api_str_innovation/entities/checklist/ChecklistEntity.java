package br.com.api_str_innovation.entities.checklist;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(nullable = false)
    private String tire;

    @Setter
    @Column(nullable = false)
    private String spareTire;

    @Setter
    @Column(nullable = false)
    private String kilometersNumber;

    @Setter
    @Column(nullable = false)
    private String fuelLevel;

    @Setter
    @Column(nullable = false)
    private String oilLevel;

    @Setter
    @Column(nullable = false)
    private String waterLevel;

    @Setter
    @Column(nullable = false)
    private String suspension;

    @Setter
    @Column(nullable = false)
    private String brakes;

    @Setter
    @Column(nullable = false)
    private String lights;

    @Setter
    @Column(nullable = false)
    private String glasses;

    @Setter
    @Column(nullable = false)
    private String windshieldWipers;

    @Setter
    @Column(nullable = false)
    private String jack;

    @Setter
    @Column(nullable = false)
    private String toolbox;

    @Setter
    @Column(nullable = false)
    private String documentation;

    @Setter
    private String observationNotes;

    @Column(nullable = false, updatable = false)
    private final LocalDate creationDt = LocalDate.now();

    @Setter
    private LocalDate editedDt;

    @JsonIgnore
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
