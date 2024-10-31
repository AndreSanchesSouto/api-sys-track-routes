package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.EmployeeRequest;
import br.com.api_str_innovation.dto.shipping_manager.EmployeeResponseDTO;
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

    public List<EmployeeResponseDTO> getAll() {
        List<EmployeeResponseDTO> shippingManagers = repository
                .findAll()
                .stream()
                .map(EmployeeResponseDTO::new)
                .toList();
        return shippingManagers;
    }

    public Page<EmployeeResponseDTO> getPaged(Pageable pageable) {
        Page<EmployeeResponseDTO> shippingManagers = repository
                .findAll(pageable)
                .map(EmployeeResponseDTO::new);
        return shippingManagers;
    }

    public ShippingManagerEntity getById(UUID id) {
        ShippingManagerEntity shippingManager = repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping Manager not found"));
        return shippingManager;
    }

    public void post(@Valid EmployeeRequest data) {
        System.out.println(data);
        ShippingManagerEntity shippingManagerData = new ShippingManagerEntity(data);
        repository.save(shippingManagerData);
    }

    public EmployeeResponseDTO put(UUID id, EmployeeRequest data) {
        ShippingManagerEntity shippingManager = this.getById(id);
        shippingManager.setName(data.getName());
        shippingManager.setEmail(data.getEmail());
        shippingManager.setLogin(data.getLogin());
        shippingManager.setPassword(data.getPassword());
        repository.save(shippingManager);
        return new EmployeeResponseDTO(shippingManager);
    }

    public void inactivate(UUID id) {
        ShippingManagerEntity shippingManager = getById(id);
        shippingManager.setInactivationDt(new Date());
        repository.save(shippingManager);
    }
}
