package br.com.api_str_innovation.entities.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "city")
@Entity
@Getter
@NoArgsConstructor
public class DataCityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String city;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String state;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String district;

}
