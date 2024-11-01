package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DataAddressRepository extends JpaRepository<DataAddressEntity, UUID> {
}
