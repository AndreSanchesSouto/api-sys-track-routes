package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.driver.DriverRequestDTO;
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
public abstract class AbstractEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, unique = true)
    private String login;

    @Setter
    @Column(nullable = false)
    // Implementar criptografia de senha e passar como hash
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDt = new Date();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactivationDt;

    public AbstractEmployeeEntity(DriverRequestDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.login = data.login();
        this.password = data.password();
    }

}
