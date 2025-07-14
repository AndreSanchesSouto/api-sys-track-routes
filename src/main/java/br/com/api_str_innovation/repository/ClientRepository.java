package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.client.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    @Query("""
            SELECT c FROM ClientEntity c
            WHERE c.inactivatedDt IS NULL
            AND c.generalManagerId = :generalManagerId
        """)
    Page<ClientEntity> findActiveClients(Pageable pageable, @Param("generalManagerId") UUID generalManagerId);

    @Query("""
            SELECT c FROM ClientEntity c
            WHERE LOWER(FUNCTION('unaccent', c.name))
                LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :name, '%')))
            AND c.generalManagerId = :generalManagerId
            AND c.inactivatedDt IS NULL
            """)
    Page<ClientEntity> findSearchClientsByName(
            Pageable pageable,
            @Param("name") String name,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT c FROM ClientEntity c
            WHERE LOWER(FUNCTION('unaccent', c.email))
                LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :email, '%')))
            AND c.generalManagerId = :generalManagerId
            AND c.inactivatedDt IS NULL
            """)
    Page<ClientEntity> findSearchClientsByEmail(
            Pageable pageable,
            @Param("email") String email,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT c FROM ClientEntity c
            WHERE LOWER(FUNCTION('unaccent', c.cellphone))
                LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :cellphone, '%')))
            AND c.generalManagerId = :generalManagerId
            AND c.inactivatedDt IS NULL
            """)
    Page<ClientEntity> findSearchClientsByCellphone(
            Pageable pageable,
            @Param("cellphone") String cellphone,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT c FROM ClientEntity c
            WHERE LOWER(FUNCTION('unaccent', c.document))
                LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :document, '%')))
            AND c.generalManagerId = :generalManagerId
            AND c.inactivatedDt IS NULL
            """)
    Page<ClientEntity> findSearchClientsByDocument(
            Pageable pageable,
            @Param("document") String document,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT c FROM ClientEntity c
            WHERE c.inactivatedDt IS NULL
            AND c.generalManagerId = :generalManagerId
        """)
    List<ClientEntity> findActiveClients(@Param("generalManagerId") UUID generalManagerId);

    @Query(value = """
            SELECT DISTINCT
                c.id,
                c.name,
                c.image_url,
                c.email,
                c.cellphone,
                c.document,
                c.created_dt,
                c.inactivated_dt,
                c.general_manager_id
            FROM client c
                INNER JOIN address ad ON ad.client_id = c.id
            WHERE c.inactivated_dt IS NULL
            AND c.general_manager_id = :generalManagerId;
           """, nativeQuery = true)
    List<ClientEntity>findAvailable(@Param("generalManagerId") UUID generalManagerId);

    Optional<ClientEntity> findByEmail(String email);

    Optional<ClientEntity> findByDocument(String document);
}
