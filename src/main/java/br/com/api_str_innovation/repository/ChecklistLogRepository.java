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
    @Query(value = "WITH km_differences AS ( " +
            "    SELECT " +
            "        EXTRACT(YEAR FROM c.created_dt) AS year, " +
            "        EXTRACT(MONTH FROM c.created_dt) AS month, " +
            "        COALESCE(CAST(c.kilometers_number AS NUMERIC) - LAG(CAST(c.kilometers_number AS NUMERIC)) OVER (ORDER BY c.created_dt), 0) AS km_diff " +
            "    FROM " +
            "        checklist_log c " +
            "    WHERE " +
            "        c.vehicle_id = :vehicleId " +
            "        AND c.created_dt BETWEEN :from AND :to " +
            ") " +
            "SELECT " +
            "    year, " +
            "    month, " +
            "    SUM(km_diff) AS total_kilometers " +
            "FROM " +
            "    km_differences " +
            "GROUP BY " +
            "    year, month " +
            "ORDER BY " +
            "    year, month", nativeQuery = true)
    List<Object[]> countKmDriven(@Param("vehicleId") UUID vehicleId,
                                 @Param("from") LocalDate from,
                                 @Param("to") LocalDate to);

    @Query(value = "SELECT " +
            "    c.id, " +
            "    c.created_dt, " +
            "    CAST(c.kilometers_number AS NUMERIC) AS current_km, " +
            "    LAG(CAST(c.kilometers_number AS NUMERIC)) OVER (ORDER BY c.created_dt) AS previous_km, " +
            "    COALESCE(CAST(c.kilometers_number AS NUMERIC) - LAG(CAST(c.kilometers_number AS NUMERIC)) OVER (ORDER BY c.created_dt), 0) AS km_traveled, " +
            "    EXTRACT(EPOCH FROM (c.created_dt - LAG(c.created_dt) OVER (ORDER BY c.created_dt))) / 86400 AS days_between_checklists " +
            "FROM " +
            "    checklist_log c " +
            "WHERE " +
            "    c.vehicle_id = :vehicleId " +
            "    AND c.created_dt BETWEEN :from AND :to " +
            "ORDER BY " +
            "    c.created_dt", nativeQuery = true)
    List<Object[]> getDetailedKmSegments(@Param("vehicleId") UUID vehicleId,
                                         @Param("from") LocalDate from,
                                         @Param("to") LocalDate to);

    @Query("SELECT EXTRACT(YEAR FROM c.createdDt) AS year, EXTRACT(MONTH FROM c.createdDt) AS month, COUNT(c) AS checklistCount " +
            "FROM ChecklistLogEntity c " +
            "WHERE c.vehicleStatus = 'ACTIVE' " +
            "AND c.createdDt BETWEEN :startDate AND :endDate " +
            "GROUP BY EXTRACT(YEAR FROM c.createdDt), EXTRACT(MONTH FROM c.createdDt) " +
            "ORDER BY year, month")
    List<Object[]> countChecklistStatusVehicleActive(@Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);

    @Query("SELECT EXTRACT(YEAR FROM c.createdDt) AS year, EXTRACT(MONTH FROM c.createdDt) AS month, COUNT(c) AS checklistCount " +
            "FROM ChecklistLogEntity c " +
            "WHERE c.vehicleStatus = 'INACTIVE' " +
            "AND c.createdDt BETWEEN :startDate AND :endDate " +
            "GROUP BY EXTRACT(YEAR FROM c.createdDt), EXTRACT(MONTH FROM c.createdDt) " +
            "ORDER BY year, month")
    List<Object[]> countChecklistStatusVehicleInactive(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT " +
           "cl.created_dt, " +
           "cl.observation_notes, " +
           "cl.headlight, " +
           "cl.taillight, " +
           "cl.front_indicator, " +
           "cl.indicator, " +
           "cl.dome_light, " +
           "cl.license_plate_light, " +
           "cl.tire, " +
           "cl.glasses, " +
           "cl.rearview, " +
           "cl.license_plate, " +
           "cl.windshield_wipers, " +
           "cl.suspension, " +
           "cl.jack, " +
           "cl.brakes, " +
           "cl.spare_tire, " +
           "cl.tire_pressure, " +
           "cl.documentation, " +
           "cl.fuel_level, " +
           "cl.oil_level, " +
           "cl.water_level, " +
           "v.license_plate_number, " +
           "u.name " +
           "FROM checklist_log cl " +
           "JOIN vehicle v ON cl.vehicle_id = v.id " +
           "JOIN users u ON cl.employee_id = u.id " +
           "WHERE cl.vehicle_id = :vehicleId " +
           "AND v.general_manager_id = :generalManagerId " +
           "AND DATE(cl.created_dt) BETWEEN :from AND :to " +
           "ORDER BY cl.created_dt DESC", nativeQuery = true)
    List<Object[]> findReportData(@Param("vehicleId") UUID vehicleId,
                                  @Param("generalManagerId") UUID generalManagerId,
                                  @Param("from") LocalDate from,
                                  @Param("to") LocalDate to);

}