package br.com.api_str_innovation.entities.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name = "address")
@Entity
@Getter
public class DataAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String address_type;

    @Setter
    @Column(nullable = false)
    private String address;

    @Setter
    @Column(nullable = false)
    private String number;

    @Setter
    @Column(nullable = false)
    private String zip_code;

    @Setter
    @Column(nullable = false)
    private String reference;

}
