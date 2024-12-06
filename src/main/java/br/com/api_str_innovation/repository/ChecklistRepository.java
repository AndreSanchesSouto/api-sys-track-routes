package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChecklistRepository extends JpaRepository<ChecklistEntity, UUID> {
    @Query("SELECT c FROM ChecklistEntity c WHERE c.vehicle.id = :vehicleId")
    Page<ChecklistEntity> findChecklistsByVehicleId(@Param("vehicleId") UUID vehicleId, Pageable pageable);
}
