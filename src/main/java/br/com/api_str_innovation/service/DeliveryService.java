package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.delivery.*;
import br.com.api_str_innovation.dto.delivery.location.DeliveryLocationDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
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
import br.com.api_str_innovation.exceptions.ClientException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
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

    @Autowired
    private DeliveryProductService deliveryProductService;

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

            Integer actualQuantity = this.recalculateProductsQuantity(product.getQuantity(), productDTO.quantity(),0);
            this.productService.updateProductQuantity(product.getId(), actualQuantity);
        }
        delivery.setDeliveryProducts(deliveryProducts);
        delivery.setItems(deliveryProducts.size());
        userService.patchStatus(driver.getId(), UserStatus.UNAVAILABLE);

        this.repository.save(delivery);
        return new DeliveryResponseDTO(delivery);
    }

    public ResponseEntity<DeliveryLocationDTO> getDriverLocation(UUID id) {
        LocationProjection location = this.repository.findLocationFromDriver(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeliveryLocationDTO(
                location.getLatitude(),
                location.getLongitude(),
                this.haversineDistance(
                            location.getLatitude().doubleValue(),
                            location.getLongitude().doubleValue(),
                            this.getLatitudeDeliveryDestination(id).doubleValue(),
                            this.getLongitudeDeliveryDestination(id).doubleValue()
                        )
                ));
    }

    public BigDecimal getLatitudeDeliveryDestination(UUID id) {
        DeliveryEntity delivery = this.findById(id);
        return delivery.getAddress().getLatitude();
    }

    public BigDecimal getLongitudeDeliveryDestination(UUID id) {
        DeliveryEntity delivery = this.findById(id);
        return delivery.getAddress().getLongitude();
    }

    private boolean haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (R * c) < 1;
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
                .findDeliveries(generalManagerId, pageable)
                .map(DeliveryGenericResponseDTO::new);
    }


    public Page<DeliveryGenericResponseDTO> getSearched(Pageable pageable, String attribute, String search, UUID generalManagerId) {
        return switch (attribute) {
            case "deliveryRequest" -> repository
                    .findSearchDeliveryByDeliveryRequest(pageable, search, generalManagerId)
                    .map(DeliveryGenericResponseDTO::new);
            case "vehicle" -> repository
                    .findSearchDeliveryByVehicle(pageable, search, generalManagerId)
                    .map(DeliveryGenericResponseDTO::new);
            case "driver" -> repository
                    .findSearchDeliveryByDriver(pageable, search, generalManagerId)
                    .map(DeliveryGenericResponseDTO::new);
            case "items" -> repository
                    .findSearchDeliveryByItems(pageable, search, generalManagerId)
                    .map(DeliveryGenericResponseDTO::new);
            case "status" -> repository
                    .findSearchDeliveryByStatus(pageable, search, generalManagerId)
                    .map(DeliveryGenericResponseDTO::new);
//            case "createdDt" -> repository
//                    .findSearchDeliveryByCreatedDt(pageable, search, generalManagerId)
//                    .map(DeliveryGenericResponseDTO::new);
            default -> throw new ClientException("Parâmetro não aceito para a pesquisa");
        };
    }

    public Page<DeliveryGenericResponseDTO> getDeliveryByDriverId(UUID generalManagerId, UUID driverId, Pageable pageable) {
        return repository
                .getDeliveryByDriverId(generalManagerId, driverId, pageable)
                .map(DeliveryGenericResponseDTO::new);
    }

    public Page<DeliveryResponseDTO> getByStatus(String status, Pageable pageable, UUID generalManagerId) {
        return repository
                .findByStatusAndGeneralManagerId(status.toLowerCase(), generalManagerId, pageable)
                .map(DeliveryResponseDTO::new);
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
        if(deliveryEntity.getStatus().equals(DeliveryStatus.CANCELED.getStatus())) {
            throw new DeliveryException("Entrega inativa");
        }
        ClientEntity client = this.clientService.getById(data.clientId());
        DataAddressEntity address = this.addressService.findById(data.addressId());
        this.validateAddressToClient(client, address);

        UserEntity driver = this.userService.findById(data.driverId());
        this.validateUserType(driver);

        VehicleEntity vehicle = this.vehicleService.findById(data.vehicleId());

        deliveryEntity.setClient(client);
        deliveryEntity.setAddress(address);
        deliveryEntity.setVehicle(vehicle);
        deliveryEntity.setDriver(driver);

        for (DeliveryProductRequestDTO productDTO : data.products()) {
            ProductEntity product = productService.findById(productDTO.productId());

            Optional<DeliveryProductEntity> deliveryProductEntity = this.deliveryProductService.getByDeliveryIdAndProductId(
                    deliveryEntity.getId(),
                    product.getId()
            );

            if(deliveryProductEntity.isPresent()) {
                Integer actualQuantity = this.recalculateProductsQuantity(
                        product.getQuantity(),
                        productDTO.quantity(),
                        deliveryProductEntity.get().getQuantity()
                );
                this.productService.updateProductQuantity(product.getId(), actualQuantity);
                this.deliveryProductService.updateDeliveryProductQuantity(deliveryProductEntity.get().getId(), productDTO.quantity());
            } else {
                DeliveryProductEntity deliveryProduct = new DeliveryProductEntity(product, productDTO.quantity());
                deliveryProduct.setDelivery(deliveryEntity);
                deliveryEntity.getDeliveryProducts().add(deliveryProduct);
                Integer actualQuantity = this.recalculateProductsQuantity(product.getQuantity(), productDTO.quantity(),0);
                this.productService.updateProductQuantity(product.getId(), actualQuantity);
            }
            deliveryEntity.setItems(deliveryEntity.getDeliveryProducts().size());

        }
        userService.patchStatus(driver.getId(), UserStatus.UNAVAILABLE);
        vehicleService.patchStatus(vehicle.getId(), VehicleStatus.ON_USE);

        this.repository.save(deliveryEntity);

        List<DeliveryProductResponseDTO> deliveryProductsResponse = deliveryEntity
                .getDeliveryProducts()
                .stream()
                .map(deliveryProduct -> {
                    return new DeliveryProductResponseDTO(deliveryProduct, Integer.parseInt(productService.getById(deliveryProduct.getProductId()).quantity()));
                })
                .toList();

        return new DeliveryProductsResponseDTO(deliveryEntity, deliveryProductsResponse);
    }

    @Transactional
    public ResponseEntity<Void> registerConfirm(UUID id) {
        DeliveryEntity delivery = this.findById(id);

        VehicleEntity vehicle = this.vehicleService.findById(delivery.getVehicle().getId());
        this.vehicleService.patchStatus(vehicle.getId(), VehicleStatus.WAITING);

        UserEntity driver = this.userService.findById(delivery.getDriver().getId());
        this.userService.patchStatus(driver.getId(), UserStatus.ACTIVE);

        delivery.setStatus(DeliveryStatus.CONFIRMED.getStatus());

        this.repository.save(delivery);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    public Integer recalculateProductsQuantity(Integer productActualQuantity, Integer productNewQuantity, Integer productOldQuantity) {
        if(productActualQuantity < productNewQuantity) {
            throw new DeliveryException("Quantidade de produtos cadastrados inferior a desejada");
        }
        int difference = productOldQuantity - productNewQuantity;
        if(difference > 0) {
            return productActualQuantity + Math.abs(difference);
        } else {
            return productActualQuantity - Math.abs(difference);
        }
    }


    @Transactional
    public ResponseEntity<Void> patchStartDelivery(@PathVariable UUID id) {
        DeliveryEntity delivery = this.findById(id);

        delivery.setStatus(DeliveryStatus.ACTIVE.getStatus());

        this.repository.save(delivery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> inactiveDelivery(UUID id) {
        DeliveryEntity delivery = this.findById(id);

        VehicleEntity vehicle = this.vehicleService.findById(delivery.getVehicle().getId());
        this.vehicleService.patchStatus(vehicle.getId(), VehicleStatus.WAITING);

        UserEntity driver = this.userService.findById(delivery.getDriver().getId());
        this.userService.patchStatus(driver.getId(), UserStatus.ACTIVE);

        List<DeliveryProductEntity> deliveryProducts = delivery.getDeliveryProducts();
        deliveryProducts.forEach(
                (deliveryProduct) ->
                        this.productService.receiveReturnedProducts(
                                deliveryProduct.getProductId(),
                                deliveryProduct.getQuantity()
                        )
        );

        delivery.setStatus(DeliveryStatus.CANCELED.getStatus());

        this.repository.save(delivery);

        return new ResponseEntity<Void>(HttpStatus.OK);
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

    public List<DeliveryEntity> getAllByGeneralManagerId(UUID generalManagerId) {
        return this.repository.getAllByGeneralManagerId(generalManagerId);
    }
}
