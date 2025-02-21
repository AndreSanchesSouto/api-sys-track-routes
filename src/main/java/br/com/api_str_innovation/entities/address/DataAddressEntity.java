package br.com.api_str_innovation.entities.address;

import br.com.api_str_innovation.dto.data_address.DataAddressRequestDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "address")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String zipCode;

    @Setter
    @Column(nullable = false)
    private String street;

    @Setter
    @Column(nullable = false)
    private String number;

    @Setter
    @Column(nullable = false)
    private String addressType;

    @Setter
    @Column(nullable = false)
    private String neighborhood;

    @Setter
    @Column(nullable = false)
    private String city;

    @Setter
    @Column(nullable = false)
    private String state;

    @Setter
    @Column(nullable = false)
    private String complement;

    @Setter
    @Column(nullable = true)
    private String referencePoint;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    public DataAddressEntity(DataAddressRequestDTO data, ClientEntity client) {
        this.zipCode = data.zipCode();
        this.street = data.street();
        this.number = data.number();
        this.addressType = data.addressType();
        this.neighborhood = data.neighborhood();
        this.city = data.city();
        this.state = data.state();
        this.complement = data.complement();
        this.referencePoint = data.referencePoint();
        this.client = client;
    }
}
