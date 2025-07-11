package br.com.api_str_innovation.entities.client;

import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import br.com.api_str_innovation.entities.address.DataAddressEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "client")
@Entity
@Getter
@NoArgsConstructor
@ToString
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @Setter
    @Column(nullable = true)
    private String imageUrl;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String email;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String cellphone;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String document;

    @Setter
    @Column
    private UUID generalManagerId;

    @Setter
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DataAddressEntity> addresses;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdDt = LocalDateTime.now();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inactivated_dt")
    private LocalDateTime inactivatedDt;

    public ClientEntity(ClientRequestDTO data, UUID generalManagerId) {
        this.name = data.name();
        this.email = data.email();
        this.cellphone = data.cellphone();
        this.document = data.document();
        this.generalManagerId = generalManagerId;
    }
}
