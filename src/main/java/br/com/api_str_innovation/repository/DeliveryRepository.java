package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

    @Query("""
            SELECT d FROM DeliveryEntity d
                WHERE d.inactivatedDt IS NULL
            """)
    Page<DeliveryEntity> findDeliveries(Pageable pageable);

    @Query("""
            SELECT d FROM DeliveryEntity d
                WHERE d.inactivatedDt IS NULL
            """)
    List<DeliveryEntity> findDeliveries();

    @Query(value = """
            SELECT
            	COUNT(d.id)
             FROM deliveries d
            """, nativeQuery = true)
    Integer getDeliveryQuantity();

}
