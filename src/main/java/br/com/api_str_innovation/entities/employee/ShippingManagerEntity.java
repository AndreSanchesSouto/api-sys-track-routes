package br.com.api_str_innovation.entities.employee;

import br.com.api_str_innovation.dto.employee.EmployeeRequestDTO;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Table(name = "shipping_manager")
@Entity
@Getter
@NoArgsConstructor
public class ShippingManagerEntity extends AbstractEmployeeEntity {

    public ShippingManagerEntity(EmployeeRequestDTO data) {
        super(data);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.getRole() == Role.SHIPPING_MANAGER)
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
}
