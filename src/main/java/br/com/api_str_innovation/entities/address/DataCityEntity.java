package br.com.api_str_innovation.entities.address;

import jakarta.persistence.*;
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
    @Column(nullable = false)
    private String city;

    @Setter
    @Column(nullable = false)
    private String state;

    @Setter
    @Column(nullable = false)
    private String district;

}
