package br.com.api_str_innovation.entities.vehicle;

import br.com.api_str_innovation.dto.vehicle.VehicleRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String status;

    public VehicleEntity(VehicleRequestDTO data) {
        this.licensePlateNumber = data.licensePlateNumber();
        this.sideNumber = data.sideNumber();
        this.model = data.model();
        this.brand = data.brand();
        this.status = data.status();
    }

}
