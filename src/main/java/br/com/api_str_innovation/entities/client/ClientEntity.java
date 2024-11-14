package br.com.api_str_innovation.entities.client;

import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "client")
@Entity
@Getter
@NoArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String contactType;

    @Setter
    @Column(nullable = false)
    private String contact;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private final LocalDateTime createdDt = LocalDateTime.now();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime inactivatedDt;

    public ClientEntity(@Valid ClientRequestDTO data) {
        this.name = data.name();
        this.contactType = data.contactType();
        this.contact = data.contact();
    }

}
