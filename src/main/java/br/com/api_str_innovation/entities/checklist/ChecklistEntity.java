package br.com.api_str_innovation.entities.checklist;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private String licensePlateNumber;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private final LocalDateTime creationDt = LocalDateTime.now();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editedDt;

    public ChecklistEntity(@Valid ChecklistRequestDTO data) {
        this.tire = data.tire();
        this.licensePlateNumber = data.licensePlateNumber();
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
    }

}
