package br.com.api_str_innovation.domain.client;

import br.com.api_str_innovation.domain.enums.ContactType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name = "client")
@Entity
@Getter
@Setter
public class ClientDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType contactType;

    @Column(nullable = false)
    private String contact;
}
