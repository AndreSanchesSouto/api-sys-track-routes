package br.com.api_str_innovation.entities.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "address")
@Entity
@Getter
@NoArgsConstructor
public class DataAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String address_type;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String address;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String number;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String zip_code;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String reference;



}
