package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.product.ProductRequestDTO;
import br.com.api_str_innovation.dto.product.ProductResponseDTO;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {


    @Autowired
    private ProductRepository repository;

    private ProductEntity findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        );
    }

    public List<ProductResponseDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    public Integer count() {
        return repository
                .findActiveProducts()
                .toArray()
                .length;
    }

    @GetMapping(value = "/page")
    public Page<ProductResponseDTO> getPaged(Pageable pageable) {
        return repository
                .findActiveProducts(pageable)
                .map(ProductResponseDTO::new);
    }

    public ProductResponseDTO getById(UUID id) {
        ProductEntity product = findById(id);
        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO post(@Valid ProductRequestDTO data) {
        ProductEntity product = new ProductEntity(data);
        repository.save(product);
        return new ProductResponseDTO(product);
    }

//    public List<Object[]> periodOfCreation(PeriodTimeRequestDTO periodTimeDTO) {
//        return repository.periodTime(periodTimeDTO.from(), periodTimeDTO.to());
//    }
//
//    @Transactional
//    public void patch(@PathVariable UUID id, @RequestBody ProductRequestDTO data) {
//        existsMailOrLogin(data);
//        ProductEntity user = findById(id);
//
//        user.setName(data.name());
//        user.setEmail(data.email());
//        user.setLogin(data.login());
//        user.setStatus(data.status().getStatus());
//        repository.save(user);
//    }
//
//    @Transactional
//    public void put(@PathVariable UUID id, @RequestBody ProductRequestDTO data) {
//        existsMailOrLogin(data);
//        ProductEntity user = findById(id);
//
//        user.setName(data.name());
//        user.setEmail(data.email());
//        user.setLogin(data.login());
//        user.setStatus(data.status().getStatus());
//        repository.save(user);
//
//    }
//
//    @Transactional
//    public void inactivate(UUID id) {
//        ProductEntity user = findById(id);
//
//        if(user.getInactivatedDt() != null) {
//            throw new VehicleException("Veículo já inativo");
//        }
//
//        user.setInactivatedDt(LocalDate.now());
//        repository.save(user);
//    }

}
