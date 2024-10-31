package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.EmployeeRequest;
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
    @Column(nullable = false, updatable = false)
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

    public AbstractEmployeeEntity(EmployeeRequest data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.login = data.getLogin();
        this.password = data.getPassword();
    }

}
