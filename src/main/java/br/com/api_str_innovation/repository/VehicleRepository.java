package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.employee.DriverEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {

    @Query("SELECT v FROM VehicleEntity v WHERE v.inactivatedDt IS NULL")
    Page<VehicleEntity> findActiveVehicles(Pageable pageable);

    @Query("SELECT v FROM VehicleEntity v WHERE v.inactivatedDt IS NULL")
    List<VehicleEntity> findActiveVehicles();
}
