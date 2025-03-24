package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.delivery.*;
import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.user.UserStatus;
import br.com.api_str_innovation.entities.vehicle.VehicleStatus;
import br.com.api_str_innovation.entities.vehicle.VehicleEntity;
import br.com.api_str_innovation.exceptions.DataAddressException;
import br.com.api_str_innovation.exceptions.DeliveryException;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private DataAddressService addressService;

    @Autowired
    private UserService userService;

    public DeliveryResponseDTO post(@RequestBody DeliveryRequestDTO data) {
        ClientEntity client = this.clientService.getById(data.clientId());
        DataAddressEntity address = this.addressService.findById(data.addressId());
        this.validateAddressToClient(client, address);

        UserEntity driver = this.userService.findById(data.driverId());
        this.validateUserType(driver);

        VehicleEntity vehicle = this.vehicleService.findById(data.vehicleId());
        DeliveryEntity delivery = new DeliveryEntity();

        delivery.setClient(client);
        delivery.setAddress(address);
        delivery.setStatus(VehicleStatus.ACTIVE.getStatus());
        delivery.setVehicle(vehicle);
        delivery.setDriver(driver);
        delivery.setDeliveryRequest(this.repository.getDeliveryQuantity() + 1);

        List<DeliveryProductEntity> deliveryProducts = new ArrayList<DeliveryProductEntity>();
        for (DeliveryProductRequestDTO productDTO : data.products()) {
            ProductEntity product = productService.findById(productDTO.productId());

            DeliveryProductEntity deliveryProduct = new DeliveryProductEntity(product, productDTO.quantity());

            deliveryProduct.setDelivery(delivery);

            deliveryProducts.add(deliveryProduct);
        }
        delivery.setDeliveryProducts(deliveryProducts);

        userService.patchStatus(driver.getId(), UserStatus.UNAVAILABLE);
        vehicleService.patchStatus(vehicle.getId(), VehicleStatus.ON_USE);

        this.repository.save(delivery);
        return new DeliveryResponseDTO(delivery);
    }

    public Page<DeliveryGenericResponseDTO> getPaged(Pageable pageable) {
        return repository
                .findDeliveries(pageable)
                .map(DeliveryGenericResponseDTO::new);
    }

    public Integer count() {
        return repository
                .findDeliveries()
                .toArray()
                .length;
    }

    private void validateUserType(UserEntity driver) {
        boolean isADriver = driver.getRole().equalsIgnoreCase(Role.DRIVER.getRole());
        if(!isADriver) {
            throw new UserException("Esse usuário não é do tipo motorista");
        }
    }

    private void validateAddressToClient(ClientEntity client, DataAddressEntity addressWanted) {
        boolean addressExists = client
                .getAddresses()
                .stream()
                .anyMatch(
                        address -> address
                                .getId()
                                .equals(addressWanted.getId())
                );
        if(!addressExists) {
            throw new DataAddressException("Esse endereço não corresponde a esse cliente");
        }
    }

    public DeliveryProductsResponseDTO getById(UUID id) {
        DeliveryEntity delivery = findById(id);

        List<ProductEntity> products = delivery.getDeliveryProducts()
                .stream()
                .map(DeliveryProductEntity::getProduct)
                .toList();

        return new DeliveryProductsResponseDTO(delivery, products);
    }

    private DeliveryEntity findById(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                    new DeliveryException("Entrega não encontrada")
        );
    }
}
