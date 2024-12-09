package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.employee.ShippingManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShippingManagerRepository extends JpaRepository<ShippingManagerEntity, UUID> {

    @Query("SELECT s FROM ShippingManagerEntity s WHERE s.inactivatedDt IS NULL")
    List<ShippingManagerEntity> findActiveShippingManager();

    @Query("SELECT s FROM ShippingManagerEntity s WHERE s.login = :login AND s.password = :password")
    ShippingManagerEntity authIdentity(@Param("login") String login, @Param("password") String password);

    @Query("SELECT s FROM ShippingManagerEntity s WHERE s.login = :login")
    ShippingManagerEntity findByLogin(@Param("login") String login);

}
