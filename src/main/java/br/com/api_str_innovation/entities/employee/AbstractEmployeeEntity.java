package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.EmployeeRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @Column(nullable = false)
    private String name;

    @Setter
    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;

    @Setter
    @NotBlank
    @Column(nullable = false)
    // Implementar criptografia de senha e passar como hash
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDt = new Date();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactivationDt;

    public AbstractEmployeeEntity(EmployeeRequestDTO data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.login = data.getLogin();
        this.password = data.getPassword();
    }

}