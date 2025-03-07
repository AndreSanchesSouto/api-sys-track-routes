package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.delivery.DeliveryProductRequestDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryRequestDTO;
import br.com.api_str_innovation.dto.delivery.DeliveryResponseDTO;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository repository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ProductService productService;

    public DeliveryResponseDTO post(@RequestBody DeliveryRequestDTO data) {
        ClientEntity client = this.clientService.getById(data.clientId());
        VehicleEntity vehicle = this.vehicleService.findById(data.vehicleId());
        DeliveryEntity delivery = new DeliveryEntity();

        delivery.setClient(client);
        delivery.setStatus(data.status());
        delivery.setVehicle(vehicle);
        delivery = repository.save(delivery);

        List<DeliveryProductEntity> deliveryProducts = new ArrayList<>();
        for (DeliveryProductRequestDTO productDTO : data.products()) {
            ProductEntity product = productService.findById(productDTO.productId());

            DeliveryProductEntity deliveryProduct = new DeliveryProductEntity(product, productDTO.quantity());

            deliveryProduct.setDelivery(delivery);

            deliveryProducts.add(deliveryProduct);
        }
        delivery.setDeliveryProducts(deliveryProducts);



        this.repository.save(delivery);
        return new DeliveryResponseDTO(delivery);
    }
}
