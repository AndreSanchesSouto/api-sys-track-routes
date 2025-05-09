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
    private Boolean tire;

    @Column(nullable = false)
    private String spareTire;

    @Column(nullable = false)
    private String kilometersNumber;

    @Column(nullable = false)
    private String fuelLevel;

    @Column(nullable = false)
    private String oilLevel;

    @Column(nullable = false)
    private String waterLevel;

    @Column(nullable = false)
    private Boolean suspension;

    @Column(nullable = false)
    private String brakes;

    @Column(nullable = false)
    private Boolean glasses;

    @Column(nullable = false)
    private Boolean windshieldWipers;

    @Setter
    @Column(nullable = false)
    private Boolean rearview;

    @Setter
    @Column(nullable = false)
    private Boolean headlight;

    @Setter
    @Column(nullable = false)
    private Boolean taillight;

    @Setter
    @Column(nullable = false)
    private Boolean frontIndicator;

    @Setter
    @Column(nullable = false)
    private Boolean indicator;

    @Setter
    @Column(nullable = false)
    private Boolean domeLight;

    @Setter
    @Column(nullable = false)
    private Boolean licensePlateLight;

    @Setter
    @Column(nullable = false)
    private String tirePressure;

    @Column(nullable = false)
    private String jack;

    @Column(nullable = false)
    private String toolbox;

    @Column(nullable = false)
    private String documentation;

    private String observationNotes;

    @Setter
    @Column(nullable = false)
    private String vehicleStatus;

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
        this.glasses = data.glasses();
        this.windshieldWipers = data.windshieldWipers();
        this.rearview = data.rearview();
        this.headlight = data.headlight();
        this.taillight = data.taillight();
        this.frontIndicator = data.frontIndicator();
        this.indicator = data.indicator();
        this.domeLight = data.domeLight();
        this.licensePlateLight = data.licensePlateLight();
        this.tirePressure = data.tirePressure();
        this.jack = data.jack();
        this.toolbox = data.toolbox();
        this.documentation = data.documentation();
        this.observationNotes = data.observationNotes();
        this.employeeId = data.employeeId();
        this.employeeRole = data.employeeRole();
    }
}
