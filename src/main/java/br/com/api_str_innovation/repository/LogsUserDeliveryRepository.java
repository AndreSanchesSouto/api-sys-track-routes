package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.delivery.delivery_user_log.LogsUserDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogsUserDeliveryRepository extends JpaRepository<LogsUserDeliveryEntity, UUID> {

    //Busca todos os logs de uma entrega específica, ordenados por data/hora decrescente
    List<LogsUserDeliveryEntity> findByDeliveryIdOrderByActionDateTimeDesc(UUID deliveryId);
} 