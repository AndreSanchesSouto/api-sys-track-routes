package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("""
        SELECT p FROM ProductEntity p
            WHERE p.inactivatedDt IS NULL
    """)
    Page<ProductEntity> findActiveProducts(Pageable pageable);

    @Query("""
        SELECT p FROM ProductEntity p
            WHERE p.inactivatedDt IS NULL
    """)
    List<ProductEntity> findActiveProducts();

}
