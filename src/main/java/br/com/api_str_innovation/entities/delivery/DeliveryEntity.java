package br.com.api_str_innovation.entities.delivery;

import br.com.api_str_innovation.dto.delivery.DeliveryRequestDTO;
import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deliveries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Setter
    @Column(nullable = false, updatable = true)
    private String status;

    @Setter
    @Column(name = "delivery_request", nullable = false, updatable = true)
    private Integer deliveryRequest;

    @Setter
    @Column(nullable = false, updatable = false)
    private LocalDate createdDt = LocalDate.now();

    @Setter
    private LocalDate inactivatedDt;

    @Setter
    @Column
    private UUID generalManagerId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private ClientEntity client;

    @Setter
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = true)
    private DataAddressEntity address;

    @Setter
    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true)
    private UserEntity driver;

    @Setter
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryProductEntity> deliveryProducts = new ArrayList<>();;

    @Setter
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;
}
