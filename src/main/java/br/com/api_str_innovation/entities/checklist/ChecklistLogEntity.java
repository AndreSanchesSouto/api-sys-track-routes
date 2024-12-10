package br.com.api_str_innovation.entities.checklist;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "checklist_log")
@Getter
@Setter
@NoArgsConstructor
public class ChecklistLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String tire;

    @Column(nullable = false)
    private String spareTire;

    @Column(nullable = false)
    private Double kilometersNumber;

    @Column(nullable = false)
    private Double fuelLevel;

    @Column(nullable = false)
    private Double oilLevel;

    @Column(nullable = false)
    private Double waterLevel;

    @Column(nullable = false)
    private String suspension;

    @Column(nullable = false)
    private String brakes;

    @Column(nullable = false)
    private String lights;

    @Column(nullable = false)
    private String glasses;

    @Column(nullable = false)
    private String windshieldWipers;

    @Column(nullable = false)
    private String jack;

    @Column(nullable = false)
    private String toolbox;

    @Column(nullable = false)
    private String documentation;

    private String observationNotes;

    @Column(nullable = false, updatable = false)
    private LocalDate createdDt = LocalDate.now();

    @Column(nullable = false)
    private UUID vehicleId;

    @Column(nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    private String employeeRole;

    public ChecklistLogEntity(ChecklistRequestDTO data) {
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
