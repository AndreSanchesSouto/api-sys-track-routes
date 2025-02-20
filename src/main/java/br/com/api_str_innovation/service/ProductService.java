package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.product.ProductRequestDTO;
import br.com.api_str_innovation.dto.product.ProductResponseDTO;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.exceptions.ProductException;
import br.com.api_str_innovation.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    @GetMapping(value = "/description")
    public Page<ProductResponseDTO> getSearched(Pageable pageable, String name) {
        return repository
                .findSearchProducts(pageable, name)
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

    @Transactional
    public ProductResponseDTO patch(@PathVariable UUID id, @RequestBody ProductRequestDTO data) {
        ProductEntity product = findById(id);

        product.setName(data.name());
        product.setPrice(Double.parseDouble(data.price()));
        product.setQuantity(Integer.parseInt(data.quantity()));
        product.setMeasure(Double.parseDouble(data.measure()));
        product.setUnitValue(data.unitValue());
        product.setDescription(data.description());
        repository.save(product);

        return new ProductResponseDTO(product);
    }
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

    @Transactional
    public void inactivate(UUID id) {
        ProductEntity product = findById(id);

        if(product.getInactivatedDt() != null) {
            throw new ProductException("Veículo já inativo");
        }

        product.setInactivatedDt(LocalDate.now());
        repository.save(product);
    }

}
