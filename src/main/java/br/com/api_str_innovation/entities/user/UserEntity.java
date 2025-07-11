package br.com.api_str_innovation.entities.user;

import br.com.api_str_innovation.dto.user.UserRequestDTO;
import br.com.api_str_innovation.infrastructure.security.Encrypter;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
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

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = true)
    private String imageUrl;

    @Setter
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(unique = true, nullable = true)
    private String document;

    @Setter
    @Column(nullable = false, unique = true)
    private String login;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private String status;

    @Setter
    @Column
    private UUID generalManagerId;

    @Setter
    @Column(nullable = false)
    private String role;

    @Column(nullable = false, updatable = false)
    private final LocalDate createdDt = LocalDate.now();

    @Setter
    private LocalDate inactivatedDt;

    public UserEntity(@Valid UserRequestDTO data, UUID generalManagerId) {
        this.setName(data.name());
        this.setEmail(data.email());
        this.setLogin(data.login());
        this.setPassword(Encrypter.encrypt(data.password()));
        this.setStatus(userStatus(data.status()));
        this.setRole(data.role().getRole());
        this.setGeneralManagerId(generalManagerId);
    }

    public UserEntity(@Valid UserRequestDTO data) {
        this.setName(data.name());
        this.setEmail(data.email());
        this.setDocument(data.document());
        this.setLogin(data.login());
        this.setPassword(Encrypter.encrypt(data.password()));
        this.setStatus(userStatus(data.status()));
        this.setRole(data.role().getRole());
    }

    private String userStatus(UserStatus status) {
        return status == null ? UserStatus.ACTIVE.getStatus() : status.getStatus();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (this.role.toUpperCase()) {
            case "GENERAL_MANAGER" -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_SHIPPING"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
            case "SHIPPING_MANAGER" -> List.of(
                    new SimpleGrantedAuthority("ROLE_SHIPPING"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
            case "DRIVER" -> List.of(
                    new SimpleGrantedAuthority("ROLE_DRIVER"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
            default -> List.of(new SimpleGrantedAuthority("ROLE_USER"));
        };
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
                ", document='" + document + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdDt=" + createdDt +
                ", inactivatedDt=" + inactivatedDt +
                '}';
    }

}
