package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.client.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
}
