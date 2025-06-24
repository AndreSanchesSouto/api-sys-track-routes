package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.address.DataAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataAddressRepository extends JpaRepository<DataAddressEntity, UUID> {

    @Query(value = """
            SELECT a.* FROM address a
            JOIN client c ON c.id = a.client_id
                WHERE a.inactivated_dt IS NULL
                AND c.id = :clientId
            """, nativeQuery = true)
    List<DataAddressEntity> getAllDataAddressByClientId(@Param("clientId") UUID clientId);

}
