package br.com.api_str_innovation.service;

import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.exceptions.DeliveryException;
import br.com.api_str_innovation.repository.DeliveryProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryProductService {

    @Autowired
    private DeliveryProductRepository repository;

    public Optional<DeliveryProductEntity> getByDeliveryIdAndProductId(UUID deliveryId, UUID productId) {
        return this.repository.findByDeliveryIdAndProductId(deliveryId, productId);
    }

    public DeliveryProductEntity getById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> {
            throw new DeliveryException("Produto não encontrado em entregas");
        });
    }

    public void updateDeliveryProductQuantity(UUID id, Integer actualQuantity) {
        DeliveryProductEntity product = this.getById(id);
        product.setQuantity(actualQuantity);
        this.repository.save(product);
    }
}
