package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryProductRepository extends JpaRepository <DeliveryProductEntity, UUID> {

    @Query(value = """
            SELECT * FROM deliveries_products dp
                WHERE dp.delivery_id = :deliveryId
                AND dp.product_id = :productId
            """, nativeQuery = true)
    Optional<DeliveryProductEntity> findByDeliveryIdAndProductId(
            @Param("deliveryId") UUID deliveryId,
            @Param("productId") UUID productId
    );
}
