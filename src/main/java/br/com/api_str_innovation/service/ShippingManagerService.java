package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.shipping_manager.ShippingManagerRequestDTO;
import br.com.api_str_innovation.dto.shipping_manager.ShippingManagerResponseDTO;
import br.com.api_str_innovation.entities.employee.ShippingManagerDomain;
import br.com.api_str_innovation.repository.ShippingManagerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShippingManagerService {

    @Autowired
    private ShippingManagerRepository repository;

    public List<ShippingManagerResponseDTO> getAll() {
        List<ShippingManagerResponseDTO> shippingManagers = repository
                .findAll()
                .stream()
                .map(ShippingManagerResponseDTO::new)
                .toList();
        return shippingManagers;
    }

    public Page<ShippingManagerResponseDTO> getPaged(Pageable pageable) {
        Page<ShippingManagerResponseDTO> shippingManagers = repository
                .findAll(pageable)
                .map(ShippingManagerResponseDTO::new);
        return shippingManagers;
    }

    public ShippingManagerDomain getById(UUID id) {
        ShippingManagerDomain shippingManager = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping Manager not found"));
        return shippingManager;
    }

    public void post(@Valid ShippingManagerRequestDTO data) {
        System.out.println(data);
        ShippingManagerDomain shippingManagerData = new ShippingManagerDomain(data);
        repository.save(shippingManagerData);
    }

    public ShippingManagerResponseDTO put(UUID id, ShippingManagerRequestDTO data) {
        ShippingManagerDomain shippingManager = this.getById(id);
        shippingManager.setName(data.name());
        shippingManager.setEmail(data.email());
        shippingManager.setLogin(data.login());
        shippingManager.setPassword(data.password());
        repository.save(shippingManager);
        return new ShippingManagerResponseDTO(shippingManager);
    }

    public void inactivate(UUID id) {
        ShippingManagerDomain shippingManager = getById(id);
        shippingManager.setInactivationDt(new Date());
        repository.save(shippingManager);
    }
}
