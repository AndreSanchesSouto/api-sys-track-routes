package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.employee.GeneralManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface GeneralManagerRepository extends JpaRepository<GeneralManagerEntity, UUID> {

    @Query("SELECT g FROM GeneralManagerEntity g WHERE g.login = :login")
    UserDetails findByLogin(@Param("login") String login);

    @Query("SELECT g FROM GeneralManagerEntity g WHERE g.login = :login AND g.password = :password")
    GeneralManagerEntity authIdentity(@Param("login") String login, @Param("password") String password);

    @Query("SELECT s FROM GeneralManagerEntity s WHERE s.inactivatedDt IS NULL")
    Page<GeneralManagerEntity> findActiveGeneralManager(Pageable pageable);
}
