package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.employee.DriverEntity;
import br.com.api_str_innovation.entities.employee.GeneralManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, UUID> {

    @Query("SELECT d FROM DriverEntity d WHERE d.login = :login")
    UserDetails findByLogin(@Param("login") String login);

    @Query("SELECT d FROM DriverEntity d WHERE d.inactivatedDt IS NULL")
    Page<DriverEntity> findActiveDrivers(Pageable pageable);

    @Query("SELECT d FROM DriverEntity d WHERE d.inactivatedDt IS NULL")
    List<DriverEntity> findActiveDrivers();

    @Query("SELECT d FROM DriverEntity d WHERE d.login = :login AND d.password = :password")
    DriverEntity authIdentity(@Param("login") String login, @Param("password") String password);

    @Query("SELECT EXTRACT(YEAR FROM d.createdDt) AS year, EXTRACT(MONTH FROM d.createdDt) AS month, COUNT(d) AS driverCount " +
            "FROM DriverEntity d " +
            "WHERE d.createdDt BETWEEN :from AND :to " +
            "GROUP BY EXTRACT(YEAR FROM d.createdDt), EXTRACT(MONTH FROM d.createdDt) " +
            "ORDER BY year, month")
    List<Object[]> periodTime(@Param("from") LocalDate from, @Param("to") LocalDate to);





}
