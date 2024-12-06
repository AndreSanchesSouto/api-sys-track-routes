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

    @Setter
    @OneToMany(mappedBy = "shippingManager")
    private List<VehicleEntity> vehicles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
