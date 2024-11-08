package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.dto.driver.DriverResponseDTO;
import br.com.api_str_innovation.entities.employee.DriverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, UUID> {

    @Query("SELECT d FROM DriverEntity d WHERE d.inactivatedDt IS NULL")
    Page<DriverEntity> findActiveDrivers(Pageable pageable);

    @Query("SELECT d FROM DriverEntity d WHERE d.inactivatedDt IS NULL")
    List<DriverEntity> findActiveDrivers();

}
