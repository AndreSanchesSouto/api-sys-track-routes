package br.com.api_str_innovation.entities.checklist;

import br.com.api_str_innovation.dto.checklist.ChecklistRequestDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
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
    private String licensePlateNumber;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String spareTire;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String kilometersNumber;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String fuelLevel;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String oilLevel;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String waterLevel;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private final LocalDateTime creationDt = LocalDateTime.now();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime editedDt;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name = "vehicle_checklist",
            joinColumns = @JoinColumn(name = "checklist_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<VehicleEntity> vehicle;

    public ChecklistEntity(ChecklistRequestDTO data) {
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
