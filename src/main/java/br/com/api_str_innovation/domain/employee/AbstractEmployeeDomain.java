package br.com.api_str_innovation.domain.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEmployeeDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    // Implementar criptografia de senha e passar como hash
    private String password;

    @Column(nullable = false, updatable = false)
    private Date creationDt;

    @Column(updatable = false)
    private Date inactivationDt;
}
