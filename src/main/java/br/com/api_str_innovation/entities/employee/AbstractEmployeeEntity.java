package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.EmployeeRequestDTO;
import br.com.api_str_innovation.security.Encrypter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
    private final LocalDateTime createdDt = LocalDateTime.now();

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime inactivatedDt;

    public AbstractEmployeeEntity(EmployeeRequestDTO data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.login = data.getLogin();
        this.password = data.getPassword();
    }

    @PrePersist
    @PreUpdate
    private void encryptPassword() {
        Encrypter encrypter = new Encrypter();
        if (this.password != null) {
            this.password = encrypter.encrypt(this.password);
        }
    }
}