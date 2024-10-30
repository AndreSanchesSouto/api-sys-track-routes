package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.employee.ShippingManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShippingManagerRepository extends JpaRepository<ShippingManagerEntity, UUID> {
}
