package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.user.Status;
import br.com.api_str_innovation.entities.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT g FROM UserEntity g WHERE g.login = :login")
    Optional<UserEntity> findByLogin(@Param("login") String login);

    @Query("SELECT g FROM UserEntity g WHERE g.login = :login AND g.password = :password")
    UserEntity authIdentity(@Param("login") String login, @Param("password") String password);

    @Query("SELECT d FROM UserEntity d WHERE d.inactivatedDt IS NULL")
    Page<UserEntity> findActiveUsers(Pageable pageable);

    @Query("""
            SELECT d FROM UserEntity d WHERE d.inactivatedDt IS NULL""")
    List<UserEntity> findActiveUsers();

    @Query(""" 
            SELECT EXTRACT(YEAR FROM d.createdDt) AS year, EXTRACT(MONTH FROM d.createdDt) AS month, COUNT(d) AS driverCount
            FROM UserEntity d
            WHERE d.createdDt BETWEEN :from AND :to
            GROUP BY EXTRACT(YEAR FROM d.createdDt), EXTRACT(MONTH FROM d.createdDt)
            ORDER BY year, month
            """)
    List<Object[]> periodTime(@Param("from") LocalDate from, @Param("to") LocalDate to);

    Optional<UserEntity> findByEmail(String email);

}
