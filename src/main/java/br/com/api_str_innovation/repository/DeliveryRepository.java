package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

    @Query("""
            SELECT d FROM DeliveryEntity d
                WHERE d.inactivatedDt IS NULL
                AND d.generalManagerId = :generalManagerId
            """)
    Page<DeliveryEntity> findDeliveries(Pageable pageable, @Param("generalManagerId") UUID generalManagerId);

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

}
