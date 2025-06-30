package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.delivery_product.delivery_user_log.LogsUserDeliveryProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LogsUserDeliveryProductRepository extends JpaRepository<LogsUserDeliveryProductEntity, UUID> {
    List<LogsUserDeliveryProductEntity> findByLogId(UUID logId);
}
