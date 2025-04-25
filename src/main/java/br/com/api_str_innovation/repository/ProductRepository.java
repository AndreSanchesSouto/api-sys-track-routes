package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("""
        SELECT p FROM ProductEntity p
            WHERE p.inactivatedDt IS NULL
            AND p.generalManagerId = :generalManagerId
        """)
    Page<ProductEntity> findActiveProducts(Pageable pageable, @Param("generalManagerId") UUID generalManagerId);

    @Query("""
            SELECT p FROM ProductEntity p WHERE
            LOWER(FUNCTION('unaccent', p.name))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :name, '%')))
            AND p.generalManagerId = :generalManagerId
            """)
    Page<ProductEntity> findSearchProducts(Pageable pageable, String name, @Param("generalManagerId") UUID generalManagerId);

    @Query("""
        SELECT p FROM ProductEntity p
            WHERE p.inactivatedDt IS NULL
            AND p. generalManagerId = :generalManagerId
    """)
    List<ProductEntity> findActiveProducts(@Param("generalManagerId") UUID generalManagerId);

}
