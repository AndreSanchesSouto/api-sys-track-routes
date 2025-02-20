package br.com.api_str_innovation.entities.order;

import br.com.api_str_innovation.dto.delivery.DeliveryRequestDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.order_product.DeliveryProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Table(name = "deliveries")
@Entity
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


    @Column(nullable = false, updatable = false)
    private LocalDate createdDt = LocalDate.now();

    @Setter
    private LocalDate inactivatedDt;

    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Setter
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryProductEntity> deliveryProducts;

    public DeliveryEntity(DeliveryRequestDTO data) {
        this.setStatus(data.status());
        this.setClient(data.client());
//        this.setDeliveryProducts(data.products());
    }

}
