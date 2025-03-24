package br.com.api_str_innovation.entities.delivery_product;

import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "deliveries_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private DeliveryEntity delivery;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String unitValue;

    @Column(nullable = false)
    @Setter
    private Double price;

    @Column(nullable = false)
    @Setter
    private Double measure;

    @Column(nullable = false, updatable = false)
    private final LocalDate createdDt = LocalDate.now();

    public DeliveryProductEntity(ProductEntity product, Integer quantity) {
        this.name = product.getName();
        this.unitValue = product.getUnitValue();
        this.price = product.getPrice();
        this.measure = product.getMeasure();
        this.quantity = quantity;
    }

}

