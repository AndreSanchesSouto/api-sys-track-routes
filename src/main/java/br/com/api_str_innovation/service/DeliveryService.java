package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.checklist.ChecklistResponseDTO;
import br.com.api_str_innovation.dto.delivery.*;
import br.com.api_str_innovation.dto.delivery.location.DeliveryLocationDTO;
import br.com.api_str_innovation.entities.address.DataAddressEntity;
import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import br.com.api_str_innovation.entities.client.ClientEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryEntity;
import br.com.api_str_innovation.entities.delivery.DeliveryStatus;
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
import br.com.api_str_innovation.projections.LocationProjection;
import br.com.api_str_innovation.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private ChecklistService checklistService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DataAddressService addressService;

    @Autowired
    private UserService userService;

    public DeliveryResponseDTO post(@RequestBody DeliveryRequestDTO data, UUID generalManagerId) {
        ClientEntity client = this.clientService.getById(data.clientId());
        DataAddressEntity address = this.addressService.findById(data.addressId());
        this.validateAddressToClient(client, address);

        UserEntity driver = this.userService.findById(data.driverId());
        this.validateUserType(driver);

        VehicleEntity vehicle = this.vehicleService.findById(data.vehicleId());
        DeliveryEntity delivery = new DeliveryEntity();

        delivery.setClient(client);
        delivery.setAddress(address);
        delivery.setStatus(DeliveryStatus.WAITING.getStatus());
        delivery.setVehicle(vehicle);
        delivery.setDriver(driver);
        delivery.setDeliveryRequest(this.repository.getDeliveryQuantity() + 1);
        delivery.setGeneralManagerId(generalManagerId);

        List<DeliveryProductEntity> deliveryProducts = new ArrayList<DeliveryProductEntity>();
        for (DeliveryProductRequestDTO productDTO : data.products()) {
            ProductEntity product = productService.findById(productDTO.productId());

            DeliveryProductEntity deliveryProduct = new DeliveryProductEntity(product, productDTO.quantity());

            deliveryProduct.setDelivery(delivery);

            deliveryProducts.add(deliveryProduct);
        }
        delivery.setDeliveryProducts(deliveryProducts);

        userService.patchStatus(driver.getId(), UserStatus.UNAVAILABLE);

        this.repository.save(delivery);
        return new DeliveryResponseDTO(delivery);
    }

    public ResponseEntity<DeliveryLocationDTO> getDriverLocation(UUID id) {
        LocationProjection location = this.repository.findLocationFromDriver(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeliveryLocationDTO(location.getLatitude(), location.getLongitude()));
    }

    @Transactional
    public ResponseEntity<Void> sendCurrentLocation(UUID id, @Valid DeliveryLocationDTO data) {
        DeliveryEntity delivery = this.repository.getReferenceById(id);
        delivery.setLatitude(data.latitude());
        delivery.setLongitude(data.longitude());
        this.repository.save(delivery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Page<DeliveryGenericResponseDTO> getPaged(Pageable pageable, UUID generalManagerId) {
        return repository
                .findDeliveries(pageable, generalManagerId)
                .map(DeliveryGenericResponseDTO::new);
    }

    public Page<DeliveryGenericResponseDTO> getDeliveryByDriverId(UUID generalManagerId, UUID driverId, Pageable pageable) {
        return repository
                .getDeliveryByDriverId(generalManagerId, driverId, pageable)
                .map(DeliveryGenericResponseDTO::new);
    }

    public Integer count(UUID generalManagerId) {
        return repository
                .findDeliveries(generalManagerId)
                .toArray()
                .length;
    }

    @Transactional
    public DeliveryProductsResponseDTO patch(@PathVariable UUID id, @Valid @RequestBody DeliveryRequestDTO data) {
        DeliveryEntity deliveryEntity = findById(id);
        ClientEntity client = this.clientService.getById(data.clientId());
        DataAddressEntity address = this.addressService.findById(data.addressId());

        this.validateAddressToClient(client, address);

        UserEntity driver = this.userService.findById(data.driverId());
        this.validateUserType(driver);

        VehicleEntity vehicle = this.vehicleService.findById(data.vehicleId());

        deliveryEntity.setClient(client);
        deliveryEntity.setAddress(address);
        deliveryEntity.setStatus(VehicleStatus.ACTIVE.getStatus());
        deliveryEntity.setVehicle(vehicle);
        deliveryEntity.setDriver(driver);
        deliveryEntity.getDeliveryProducts().clear();

        for (DeliveryProductRequestDTO productDTO : data.products()) {
            ProductEntity product = productService.findById(productDTO.productId());

            DeliveryProductEntity deliveryProduct = new DeliveryProductEntity(product, productDTO.quantity());

            deliveryProduct.setDelivery(deliveryEntity);

            deliveryEntity.getDeliveryProducts().add(deliveryProduct);
        }

        userService.patchStatus(driver.getId(), UserStatus.UNAVAILABLE);
        vehicleService.patchStatus(vehicle.getId(), VehicleStatus.ON_USE);

        this.repository.save(deliveryEntity);

        List<DeliveryProductResponseDTO> deliveryProductsResponse =  deliveryEntity
                .getDeliveryProducts()
                .stream()
                .map(deliveryProduct -> {
                    return new DeliveryProductResponseDTO(deliveryProduct, Integer.parseInt(productService.getById(deliveryProduct.getProductId()).quantity()));
                })
                .toList();

        return new DeliveryProductsResponseDTO(deliveryEntity, deliveryProductsResponse);
    }

    @Transactional
    public ResponseEntity<Void> patchStartDelivery(@PathVariable UUID id) {
        DeliveryEntity delivery = this.findById(id);

        delivery.setStatus(DeliveryStatus.ACTIVE.getStatus());

        this.repository.save(delivery);
        return new ResponseEntity<>(HttpStatus.OK);
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

        List<DeliveryProductResponseDTO> deliveryProductsResponse =  delivery
                .getDeliveryProducts()
                .stream()
                .map(deliveryProduct -> {
                    return new DeliveryProductResponseDTO(deliveryProduct, Integer.parseInt(productService.getById(deliveryProduct.getProductId()).quantity()));
                })
                .toList();
        VehicleEntity vehicle = this.vehicleService.findById(delivery.getVehicle().getId());

        Optional<ChecklistEntity> checklist = this.checklistService.findByVehicleId(vehicle.getId());

        return checklist.map(checklistEntity ->
                new DeliveryProductsResponseDTO(
                        delivery,
                        deliveryProductsResponse,
                        checklistEntity.getCreationDt().toString(),
                        checklistEntity.getEmployeeId()
                )
        ).orElseGet(() ->
                new DeliveryProductsResponseDTO(delivery, deliveryProductsResponse)
        );

    }

    private DeliveryEntity findById(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                    new DeliveryException("Entrega não encontrada")
        );
    }
}
