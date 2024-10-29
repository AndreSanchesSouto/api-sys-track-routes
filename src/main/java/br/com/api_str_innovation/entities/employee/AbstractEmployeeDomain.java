package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dtos.driver.DriverRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@NoArgsConstructor
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDt = new Date();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactivationDt;

    protected AbstractEmployeeDomain(DriverRequestDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.login = data.login();
        this.password = data.password();
    }

}
