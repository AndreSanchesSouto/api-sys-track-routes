package br.com.api_str_innovation.entities.delivery;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "logs_user_delivery")
@Getter
@Setter
@NoArgsConstructor
public class LogsUserDeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private UUID deliveryId;

    @Column(nullable = false)
    private LocalDateTime actionDateTime;

    @Column(nullable = false)
    private String description;

    public LogsUserDeliveryEntity(UUID userId, String userName, String action, UUID deliveryId) {
        this.userId = userId;
        this.userName = userName;
        this.action = action;
        this.deliveryId = deliveryId;
        this.actionDateTime = LocalDateTime.now();
        this.description = String.format("%s %s entrega às %s do dia %s",
            userName, action.toLowerCase(), 
            this.actionDateTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")),
            this.actionDateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy")));
    }
} 