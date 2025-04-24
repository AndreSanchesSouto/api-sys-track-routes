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

    @Query("SELECT c FROM ClientEntity c WHERE c.inactivatedDt IS NULL")
    Page<ClientEntity> findActiveClients(Pageable pageable);

    @Query("SELECT c FROM ClientEntity c WHERE " +
            "LOWER(FUNCTION('unaccent', c.name)) " +
            "LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :name, '%')))")
    Page<ClientEntity> findSearchClients(Pageable pageable, String name);

    @Query("SELECT c FROM ClientEntity c WHERE c.inactivatedDt IS NULL")
    List<ClientEntity> findActiveClients();

    Optional<ClientEntity> findByEmail(String email);

    Optional<ClientEntity> findByDocument(String document);
}
