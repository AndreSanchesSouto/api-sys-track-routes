package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.employee.EmployeeRequestDTO;
import br.com.api_str_innovation.security.Encrypter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class AbstractEmployeeEntity implements UserDetails {

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
    private String password;

    @Setter
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, updatable = false)
    private final LocalDate createdDt = LocalDate.now();

    @Setter
    private LocalDate inactivatedDt;

    public AbstractEmployeeEntity(EmployeeRequestDTO data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.login = data.getLogin();
        this.password = Encrypter.encrypt(data.getPassword());
        this.role = data.getRole();
    }


    @Override
    public String toString() {
        return "AbstractEmployeeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdDt=" + createdDt +
                ", inactivatedDt=" + inactivatedDt +
                '}';
    }
}