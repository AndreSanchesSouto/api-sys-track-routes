package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.shipping_manager.ShippingManagerRequestDTO;
import br.com.api_str_innovation.dto.shipping_manager.ShippingManagerResponseDTO;
import br.com.api_str_innovation.entities.employee.ShippingManagerEntity;
import br.com.api_str_innovation.repository.ShippingManagerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    public ShippingManagerEntity getById(UUID id) {
        ShippingManagerEntity shippingManager = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping Manager not found"));
        return shippingManager;
    }

    public void post(@Valid ShippingManagerRequestDTO data) {
        System.out.println(data);
        ShippingManagerEntity shippingManagerData = new ShippingManagerEntity(data);
        repository.save(shippingManagerData);
    }

    public ShippingManagerResponseDTO put(UUID id, ShippingManagerRequestDTO data) {
        ShippingManagerEntity shippingManager = this.getById(id);
        shippingManager.setName(data.getName());
        shippingManager.setEmail(data.getEmail());
        shippingManager.setLogin(data.getLogin());
        shippingManager.setPassword(data.getPassword());
        repository.save(shippingManager);
        return new ShippingManagerResponseDTO(shippingManager);
    }

    public void inactivate(UUID id) {
        ShippingManagerEntity shippingManager = getById(id);
        shippingManager.setInactivationDt(new Date());
        repository.save(shippingManager);
    }

}
