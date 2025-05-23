package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {

    @Query("""
            SELECT v FROM VehicleEntity v
                WHERE v.inactivatedDt IS NULL
                AND v.generalManagerId = :generalManagerId
            """)
    Page<VehicleEntity> findActiveVehicles(Pageable pageable, @Param("generalManagerId") UUID generalManagerId);

    @Query("""
            SELECT v FROM VehicleEntity v
            WHERE LOWER(FUNCTION('unaccent', v.licensePlateNumber))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :licensePlateNumber, '%')))
            AND v.generalManagerId = :generalManagerId
            """)
    Page<VehicleEntity> findSearchedVehicles(
            Pageable pageable,
            @Param("licensePlateNumber") String licensePlateNumber,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT v FROM VehicleEntity v
                WHERE v.inactivatedDt IS NULL
                AND v.generalManagerId = :generalManagerId
            """)
    List<VehicleEntity> findActiveVehicles(@Param("generalManagerId") UUID generalManagerId);

    Optional<VehicleEntity> findByLicensePlateNumber(String licensePlateNumber);

    @Modifying
    @Query("""
            UPDATE VehicleEntity v
                SET v.status = :status
            WHERE v.id = :vehicleId
            """)
    void updateStatusVehicle(
            @Param("status") String status,
            @Param("vehicleId") UUID vehicleId
    );

    @Query("""
            SELECT v FROM VehicleEntity v
                WHERE v.checklist.id = :checklistId
            """)
    Optional<VehicleEntity> findVehicleFromChecklistId(
            @Param("checklistId") UUID checklistId
    );

    @Query(value = """
            SELECT * FROM vehicle v
                WHERE v.status = 'waiting'
                AND v.inactivated_dt IS NULL
                AND v.general_manager_id = :generalManagerId
            """, nativeQuery = true)
    List<VehicleEntity> findAvailableVehicles(@Param("generalManagerId") UUID generalManagerId);

}
