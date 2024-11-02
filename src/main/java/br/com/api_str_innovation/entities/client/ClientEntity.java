package br.com.api_str_innovation.entities.client;

import br.com.api_str_innovation.dto.client.ClientRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Setter
    @Column(nullable = false)
    private String status;

    public ClientEntity(@Valid ClientRequestDTO data) {
        this.name = data.name();
        this.contactType = data.contactType();
        this.contact = data.contact();
        this.status = data.status();
    }

}
