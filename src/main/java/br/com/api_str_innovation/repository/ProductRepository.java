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
            AND p.inactivatedDt IS NULL
            """)
    Page<ProductEntity> findSearchProductsByName(
            Pageable pageable,
            @Param("name") String name,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT p FROM ProductEntity p WHERE
            LOWER(FUNCTION('unaccent', p.price))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :price, '%')))
            AND p.generalManagerId = :generalManagerId
            AND p.inactivatedDt IS NULL
            """)
    Page<ProductEntity> findSearchProductsByPrice(
            Pageable pageable,
            @Param("price") Double price,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT p FROM ProductEntity p WHERE
            LOWER(FUNCTION('unaccent', p.measure))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :measure, '%')))
            AND p.generalManagerId = :generalManagerId
            AND p.inactivatedDt IS NULL
            """)
    Page<ProductEntity> findSearchProductsByMeasure(
            Pageable pageable,
            @Param("measure") Double measure,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT p FROM ProductEntity p WHERE
            LOWER(FUNCTION('unaccent', p.price))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :price, '%')))
            AND p.generalManagerId = :generalManagerId
            AND p.inactivatedDt IS NULL
            """)
    Page<ProductEntity> findSearchProductsByQuantity(
            Pageable pageable,
            @Param("price") Integer price,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT p FROM ProductEntity p WHERE
            LOWER(FUNCTION('unaccent', p.description))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :description, '%')))
            AND p.generalManagerId = :generalManagerId
            AND p.inactivatedDt IS NULL
            """)
    Page<ProductEntity> findSearchProductsByDescription(
            Pageable pageable,
            @Param("description") String description,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT p FROM ProductEntity p WHERE
            LOWER(FUNCTION('unaccent', p.unitValue))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :unitValue, '%')))
            AND p.generalManagerId = :generalManagerId
            AND p.inactivatedDt IS NULL
            """)
    Page<ProductEntity> findSearchProductsByUnitValue(
            Pageable pageable,
            @Param("unitValue") String unitValue,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
        SELECT p FROM ProductEntity p
            WHERE p.inactivatedDt IS NULL
            AND p. generalManagerId = :generalManagerId
    """)
    List<ProductEntity> findActiveProducts(@Param("generalManagerId") UUID generalManagerId);

}
