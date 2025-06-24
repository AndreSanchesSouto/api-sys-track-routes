package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.projections.LocationProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

    @Query(value = """
            SELECT d
            FROM DeliveryEntity d
            LEFT JOIN FETCH d.client client
            LEFT JOIN FETCH d.address address
            LEFT JOIN FETCH d.driver driver
            LEFT JOIN FETCH d.vehicle vehicle
            LEFT JOIN FETCH d.deliveryProducts deliveryProducts
            WHERE d.inactivatedDt IS NULL
            AND d.generalManagerId = :generalManagerId
        """,
        countQuery = """
            SELECT COUNT(d)
            FROM DeliveryEntity d
            WHERE d.inactivatedDt IS NULL
            AND d.generalManagerId = :generalManagerId
        """)
    Page<DeliveryEntity> findDeliveries(@Param("generalManagerId") UUID generalManagerId, Pageable pageable);

    @Query("""
            SELECT d FROM DeliveryEntity d
            WHERE CAST(d.deliveryRequest AS string) LIKE CONCAT('%', :deliveryRequest, '%')
            AND d.generalManagerId = :generalManagerId
            AND d.inactivatedDt IS NULL
            """)
    Page<DeliveryEntity> findSearchDeliveryByDeliveryRequest(
            Pageable pageable,
            @Param("deliveryRequest") String deliveryRequest,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT d FROM DeliveryEntity d
            JOIN d.vehicle vehicle
            WHERE LOWER(FUNCTION('unaccent', vehicle.licensePlateNumber))
                LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :licensePlateNumber, '%')))
            AND d.generalManagerId = :generalManagerId
            AND d.inactivatedDt IS NULL
            """)
    Page<DeliveryEntity> findSearchDeliveryByVehicle(
            Pageable pageable,
            @Param("licensePlateNumber") String licensePlateNumber,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT d FROM DeliveryEntity d
            JOIN d.driver driver
            WHERE LOWER(FUNCTION('unaccent', driver.name))
                LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :driver, '%')))
            AND d.generalManagerId = :generalManagerId
            AND d.inactivatedDt IS NULL
            """)
    Page<DeliveryEntity> findSearchDeliveryByDriver(
            Pageable pageable,
            @Param("driver") String driver,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT d FROM DeliveryEntity d
            WHERE CAST(d.items AS string) LIKE CONCAT('%', :items, '%')
            AND d.generalManagerId = :generalManagerId
            AND d.inactivatedDt IS NULL
            """)
    Page<DeliveryEntity> findSearchDeliveryByItems(
            Pageable pageable,
            @Param("items") String items,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT d FROM DeliveryEntity d
            WHERE LOWER(FUNCTION('unaccent', d.status))
                LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :status, '%')))
            AND d.generalManagerId = :generalManagerId
            AND d.inactivatedDt IS NULL
            """)
    Page<DeliveryEntity> findSearchDeliveryByStatus(
            Pageable pageable,
            @Param("status") String status,
            @Param("generalManagerId") UUID generalManagerId
    );

//    @Query("""
//    SELECT d FROM DeliveryEntity d
//    WHERE
//        FUNCTION('TO_CHAR', d.createdDt, 'YYYY-MM-DD') LIKE CONCAT('%', :searchTerm, '%')
//    AND d.generalManagerId = :generalManagerId
//    AND d.inactivatedDt IS NULL
//    """)
//    Page<DeliveryEntity> findSearchDeliveryByCreatedDt(
//            Pageable pageable,
//            @Param("searchTerm") String searchTerm,
//            @Param("generalManagerId") UUID generalManagerId
//    );

    @Transactional
    @Query(value = """
            UPDATE deliveries
                SET latitude = :latitude,
                longitude = :longitude
            WHERE id = :id
            """, nativeQuery = true)
    DeliveryEntity updateLocation(
            @Param("id") UUID id,
            @Param("latitude")BigDecimal latitude,
            @Param("longitude")BigDecimal longitude
    );

    @Query("""
            SELECT d FROM DeliveryEntity d
                WHERE d.inactivatedDt IS NULL
                AND d.generalManagerId = :generalManagerId
            """)
    List<DeliveryEntity> findDeliveries(@Param("generalManagerId") UUID generalManagerId);

    @Query(value = """
            SELECT
            	COUNT(d.id)
             FROM deliveries d
            """, nativeQuery = true)
    Integer getDeliveryQuantity();

    @Query(value = """
            SELECT
                latitude,
                longitude
            FROM deliveries
            WHERE id = :id
            """, nativeQuery = true)
    LocationProjection findLocationFromDriver(@Param("id") UUID id);

    @Query("""
            SELECT d FROM DeliveryEntity d
                WHERE d.driver.id = :driverId
                AND d.generalManagerId = :generalManagerId
            """
    )
    Page<DeliveryEntity> getDeliveryByDriverId(
            @Param("generalManagerId") UUID generalManagerId,
            @Param("driverId") UUID driverId,
            Pageable pageable
    );

    @Query(value = """
            SELECT * FROM deliveries WHERE general_manager_id = :generalManagerId AND status = :status
            """, nativeQuery = true)
    Page<DeliveryEntity> findByStatusAndGeneralManagerId(
            @Param("status") String status,
            @Param("generalManagerId")UUID generalManagerId,
            Pageable pageable
    );

    @Query(value = """
            SELECT * FROM deliveries WHERE general_manager_id = :generalManagerId
            """, nativeQuery = true)
    List<DeliveryEntity> getAllByGeneralManagerId(@Param("generalManagerId") UUID generalManagerId);
}
