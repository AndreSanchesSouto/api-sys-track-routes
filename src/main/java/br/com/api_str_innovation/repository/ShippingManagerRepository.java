package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.domain.employee.ShippingManagerDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShippingManagerRepository extends JpaRepository<ShippingManagerDomain, UUID> {
}
