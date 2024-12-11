package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.checklist.ChecklistLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChecklistLogRepository extends JpaRepository<ChecklistLogEntity, UUID> {
    @Query(value = "WITH vehicle_km_log AS ( " +
            "    SELECT " +
            "        c.vehicle_id, " +
            "        c.kilometers_number, " +
            "        LAG(c.kilometers_number) OVER (PARTITION BY c.vehicle_id ORDER BY c.created_dt) AS previous_kilometers, " +
            "        c.created_dt " +
            "    FROM " +
            "        checklist_log c " +
            "    WHERE " +
            "        c.vehicle_id = :vehicleId " +
            "        AND c.created_dt BETWEEN :startDate AND :endDate " +
            ") " +
            ", filtered_km_log AS ( " +
            "    SELECT " +
            "        EXTRACT(YEAR FROM created_dt) AS year, " +
            "        EXTRACT(MONTH FROM created_dt) AS month, " +
            "        ROW_NUMBER() OVER (PARTITION BY EXTRACT(YEAR FROM created_dt), EXTRACT(MONTH FROM created_dt) ORDER BY created_dt) AS rn, " +
            "        kilometers_number, " +
            "        previous_kilometers " +
            "    FROM " +
            "        vehicle_km_log " +
            ") " +
            ", monthly_km_log AS ( " +
            "    SELECT " +
            "        year, " +
            "        month, " +
            "        SUM(CASE WHEN rn > 1 THEN COALESCE(kilometers_number - previous_kilometers, 0) ELSE 0 END) AS kilometers_diff " +
            "    FROM " +
            "        filtered_km_log " +
            "    WHERE " +
            "        previous_kilometers IS NOT NULL " +
            "    GROUP BY " +
            "        year, " +
            "        month " +
            ") " +
            "SELECT " +
            "    year, " +
            "    month, " +
            "    kilometers_diff AS total_kilometers " +
            "FROM " +
            "    monthly_km_log " +
            "ORDER BY " +
            "    year, month", nativeQuery = true)
    List<Object[]> countKmDriven(@Param("vehicleId") UUID vehicleId,
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