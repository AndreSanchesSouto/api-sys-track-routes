package br.com.api_str_innovation.entities.delivery_product.delivery_user_log;

import br.com.api_str_innovation.entities.delivery.delivery_user_log.LogsUserDeliveryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "logs_user_delivery_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogsUserDeliveryProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double measure;

    @Column(nullable = false)
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", nullable = false)
    private LogsUserDeliveryEntity log;
}