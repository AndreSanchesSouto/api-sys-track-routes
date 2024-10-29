package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.employee.DriverDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<DriverDomain, UUID> {
}
