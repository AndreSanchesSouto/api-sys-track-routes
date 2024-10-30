package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {
}
