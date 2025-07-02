package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.dto.vehicle.VehicleResponseDTO;
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
            AND v.inactivatedDt IS NULL
            """)
    Page<VehicleEntity> findSearchedVehiclesByPlate(
            Pageable pageable,
            @Param("licensePlateNumber") String licensePlateNumber,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT v FROM VehicleEntity v
            WHERE LOWER(FUNCTION('unaccent', v.sideNumber))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :sideNumber, '%')))
            AND v.generalManagerId = :generalManagerId
            AND v.inactivatedDt IS NULL
            """)
    Page<VehicleEntity> findSearchedVehiclesBySideNumber(
            Pageable pageable,
            @Param("sideNumber") String sideNumber,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT v FROM VehicleEntity v
            WHERE LOWER(FUNCTION('unaccent', v.model))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :model, '%')))
            AND v.generalManagerId = :generalManagerId
            AND v.inactivatedDt IS NULL
            """)
    Page<VehicleEntity> findSearchedVehiclesByModel(
            Pageable pageable,
            @Param("model") String model,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT v FROM VehicleEntity v
            WHERE LOWER(FUNCTION('unaccent', v.brand))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :brand, '%')))
            AND v.generalManagerId = :generalManagerId
            AND v.inactivatedDt IS NULL
            """)
    Page<VehicleEntity> findSearchedVehiclesByBrand(
            Pageable pageable,
            @Param("brand") String brand,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT v FROM VehicleEntity v
            WHERE LOWER(FUNCTION('unaccent', v.yearDt))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :year, '%')))
            AND v.generalManagerId = :generalManagerId
            AND v.inactivatedDt IS NULL
            """)
    Page<VehicleEntity> findSearchedVehiclesByYear(
            Pageable pageable,
            @Param("year") String year,
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
                WHERE v.status IN ( 'waiting', 'active')
                AND v.inactivated_dt IS NULL
                AND v.general_manager_id = :generalManagerId
            """, nativeQuery = true)
    List<VehicleEntity> findAvailableVehicles(@Param("generalManagerId") UUID generalManagerId);

    @Query(value = """
            SELECT * FROM vehicle WHERE general_manager_id = :generalManagerId AND inactivated_dt IS NULL
            """, nativeQuery = true)
    List<VehicleEntity> getAllByGeneralManagerId(@Param("generalManagerId")UUID generalManagerId);

    @Query(value = """
            SELECT * FROM vehicle WHERE general_manager_id = :generalManagerId AND status = :status
            """, nativeQuery = true)
    Page<VehicleEntity> findByStatusAndGeneralManagerId(
            @Param("status") String status,
            @Param("generalManagerId")UUID generalManagerId,
            Pageable pageable
    );
}
