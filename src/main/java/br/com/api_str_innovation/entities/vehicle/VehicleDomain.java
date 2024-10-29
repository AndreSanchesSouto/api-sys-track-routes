package br.com.api_str_innovation.entities.vehicle;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name = "vehicle")
@Entity
@Getter
@Setter
public class VehicleDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String licensePlateNumber;

    @Column(nullable = false)
    private String sideNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;
}
