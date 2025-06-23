package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u WHERE u.login = :login")
    Optional<UserEntity> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserDetails> findUserDetailsById(@Param("id") UUID id);

    @Query("SELECT u FROM UserEntity u WHERE u.login = :login AND u.password = :password")
    Optional<UserEntity> authIdentity(@Param("login") String login, @Param("password") String password);

    @Query("SELECT u FROM UserEntity u WHERE u.inactivatedDt IS NULL AND u.generalManagerId = :generalManagerId")
    Page<UserEntity> findActiveUsers(Pageable pageable, @Param("generalManagerId") UUID generalManagerId);

    @Query(value = """
            SELECT * FROM users WHERE general_manager_id = :generalManagerId AND status = :status
            """, nativeQuery = true)
    Page<UserEntity> findByStatusAndGeneralManagerId(
            @Param("status") String status,
            @Param("generalManagerId")UUID generalManagerId,
            Pageable pageable
    );

    @Query("""
            SELECT u FROM UserEntity u WHERE
            LOWER(FUNCTION('unaccent', u.name))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :name, '%')))
            AND u.generalManagerId = :generalManagerId
            AND u.inactivatedDt IS NULL
            """)
    Page<UserEntity> findSearchClientsByName(
            Pageable pageable,
            @Param("name") String name,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT u FROM UserEntity u WHERE
            LOWER(FUNCTION('unaccent', u.email))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :email, '%')))
            AND u.generalManagerId = :generalManagerId
            AND u.inactivatedDt IS NULL
            """)
    Page<UserEntity> findSearchClientsByEmail(
            Pageable pageable,
            @Param("email") String email,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT u FROM UserEntity u WHERE
            LOWER(FUNCTION('unaccent', u.login))
            LIKE LOWER(FUNCTION('unaccent', CONCAT('%', :login, '%')))
            AND u.generalManagerId = :generalManagerId
            AND u.inactivatedDt IS NULL
            """)
    Page<UserEntity> findSearchClientsByLogin(
            Pageable pageable,
            @Param("login") String login,
            @Param("generalManagerId") UUID generalManagerId
    );

    @Query("""
            SELECT u FROM UserEntity u WHERE u.inactivatedDt IS NULL
            AND u.generalManagerId = :generalManagerId
            """)
    List<UserEntity> findActiveUsers(@Param("generalManagerId") UUID generalManagerId);

    @Query(""" 
            SELECT EXTRACT(YEAR FROM d.createdDt) AS year, EXTRACT(MONTH FROM d.createdDt) AS month, COUNT(d) AS driverCount
            FROM UserEntity d
            WHERE d.createdDt BETWEEN :from AND :to
            GROUP BY EXTRACT(YEAR FROM d.createdDt), EXTRACT(MONTH FROM d.createdDt)
            ORDER BY year, month
            """)
    List<Object[]> periodTime(@Param("from") LocalDate from, @Param("to") LocalDate to);

    Optional<UserEntity> findByEmail(String email);

    @Query(value = """
            SELECT
                *
            FROM users u
                WHERE u.role = :role
                AND u.inactivated_dt IS NULL
                AND u.status = :status
                AND u.general_manager_id = :generalManagerId
            """, nativeQuery = true)
    List<UserEntity> findUsersActivated(@Param("role") String role,
                                        @Param("status") String status,
                                        @Param("generalManagerId") UUID generalManagerId
    );

    Optional<UserEntity> findByDocument(String document);

    @Query(value = """
            SELECT * FROM users
            WHERE general_manager_id = :generalManagerId
            AND inactivated_dt IS NULL AND
            role = 'driver'
            """, nativeQuery = true)
    List<UserEntity> getAllDriversByGeneralManagerId(@Param("generalManagerId") UUID generalManagerId);
}
