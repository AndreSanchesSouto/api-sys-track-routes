package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.checklist.LogsUserChecklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogsUserChecklistRepository extends JpaRepository<LogsUserChecklistEntity, UUID> {

    //Busca todos os logs de um veículo específico, ordenados por data/hora decrescente
    List<LogsUserChecklistEntity> findByVehicleIdOrderByActionDateTimeDesc(UUID vehicleId);
} 