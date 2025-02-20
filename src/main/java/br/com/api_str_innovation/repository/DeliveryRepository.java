package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.order.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {



}
