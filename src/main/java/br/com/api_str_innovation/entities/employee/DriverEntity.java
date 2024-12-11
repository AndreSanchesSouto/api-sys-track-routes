package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.employee.driver.DriverRequestDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Table(name = "driver")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DriverEntity extends AbstractEmployeeEntity {

    private String status;

    public DriverEntity(DriverRequestDTO data) {
        super(data);
        this.status = data.getStatus() == null ? "ACTIVE" : data.getStatus().toUpperCase();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.getRole() == Role.DRIVER)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        return null;
    }

    @Override
    public String getUsername() {
        return this.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public String toString() {
        return "DriverEntity{" +super.toString() +
                "status='" + status + '\'' +
                '}';
    }

}
