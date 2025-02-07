package br.com.api_str_innovation.entities.product;

import br.com.api_str_innovation.dto.product.ProductRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column
    @Setter
    private String description;

    @Column(nullable = false)
    @Setter
    private Integer quantity;

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

    @Setter
    private LocalDate inactivatedDt;

    public ProductEntity(@Valid ProductRequestDTO data) {
        this.name = data.name();
        this.description = data.description() != null ? data.description() : "";
        this.unitValue = data.unitValue();
        this.quantity = Integer.parseInt(data.quantity());
        this.measure = Double.parseDouble(data.measure());
        this.price = Double.parseDouble(data.price());
    }
}
