package br.com.api_str_innovation.entities.order_product;

import br.com.api_str_innovation.entities.order.DeliveryEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deliveries_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private DeliveryEntity delivery;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    public DeliveryProductEntity(DeliveryEntity delivery, ProductEntity product) {
        this.delivery = delivery;
        this.product = product;
        this.quantity = 1;
    }

}

