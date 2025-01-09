package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.employee.UserRequestDTO;
import br.com.api_str_innovation.security.Encrypter;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "general_manager")
@Entity
@Getter
@NoArgsConstructor
public class UserEntity implements UserDetails {

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
    @NotBlank
    @Column(nullable = false)
    private String status;

    @Setter
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, updatable = false)
    private final LocalDate createdDt = LocalDate.now();

    @Setter
    private LocalDate inactivatedDt;

    public UserEntity(String name, String email, String login, String hashPassword, Role role) {
        this.setName(name);
        this.setEmail(email);
        this.setLogin(login);
        this.setPassword(hashPassword);
        this.setRole(role);
    }

    public UserEntity(@Valid UserRequestDTO data) {
        this.setName(data.name());
        this.setEmail(data.email());
        this.setLogin(data.login());
        this.setPassword(Encrypter.encrypt(data.password()));
        this.setRole(data.role());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.getRole() == Role.GENERAL_MANAGER){
            return List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getUsername() {
        return this.getLogin();
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
