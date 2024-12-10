package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.checklist.ChecklistLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ChecklistLogRepository extends JpaRepository<ChecklistLogEntity, UUID> {
    @Query(value = "WITH vehicle_km_log AS ( " +
            "    SELECT " +
            "        c.kilometers_number, " +
            "        LAG(c.kilometers_number) OVER (PARTITION BY c.vehicle_id ORDER BY c.created_dt) AS previous_kilometers " +
            "    FROM " +
            "        checklist_log c " +
            "    WHERE " +
            "        c.vehicle_id = :vehicleId " +
            "        AND c.created_dt BETWEEN :startDate AND :endDate " +
            ") " +
            "SELECT " +
            "    SUM(COALESCE(kilometers_number - previous_kilometers, 0)) AS total_kilometers " +
            "FROM " +
            "    vehicle_km_log", nativeQuery = true)

    Double countKmDriven(@Param("vehicleId") UUID vehicleId,
                         @Param("startDate") LocalDate startDate,
                         @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(c) " +
            "FROM ChecklistLogEntity c " +
            "WHERE c.vehicleStatus = 'ACTIVE' " +
            "AND c.createdDt BETWEEN :startDate AND :endDate")
    Double countChecklistActive(@Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(c) " +
            "FROM ChecklistLogEntity c " +
            "WHERE c.vehicleStatus = 'INACTIVE' " +
            "AND c.createdDt BETWEEN :startDate AND :endDate")
    Double countChecklistInactive(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

}