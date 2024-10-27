package br.com.api_str_innovation.domain.checklist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name = "checklist")
@Entity
@Getter
@Setter
public class ChecklistDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String tire;

    @Column(nullable = false)
    private String licensePlate;

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
    private String tollBox;

    @Column(nullable = false)
    private String documentation;

    @Column(nullable = false)
    private String observationNotes;
}
