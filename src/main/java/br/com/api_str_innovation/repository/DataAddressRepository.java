package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataAddressRepository extends JpaRepository<DataAddressEntity, UUID> {
}
