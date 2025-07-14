package br.com.api_str_innovation.service;

import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.product.ProductRequestDTO;
import br.com.api_str_innovation.dto.product.ProductResponseDTO;
import br.com.api_str_innovation.entities.delivery_product.DeliveryProductEntity;
import br.com.api_str_innovation.entities.product.ProductEntity;
import br.com.api_str_innovation.exceptions.ClientException;
import br.com.api_str_innovation.exceptions.ProductException;
import br.com.api_str_innovation.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {


    @Autowired
    private ProductRepository repository;

    @Autowired
    private ImageService imageService;

    public ProductEntity findById(UUID id) {
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

    public Integer count(UUID generalManagerId) {
        return repository
                .findActiveProducts(generalManagerId)
                .toArray()
                .length;
    }

    @GetMapping(value = "/page")
    public Page<ProductResponseDTO> getPaged(Pageable pageable, UUID generalManagerId) {
        return repository
                .findActiveProducts(pageable, generalManagerId)
                .map(ProductResponseDTO::new);
    }

    public Page<ProductResponseDTO> getSearched(Pageable pageable, String attribute, String search, UUID generalManagerId) {
        return switch (attribute) {
            case "name" -> repository
                    .findSearchProductsByName(pageable, search, generalManagerId)
                    .map(ProductResponseDTO::new);
            case "price" -> parseNumberDouble(search)
                    .map(value -> repository.findSearchProductsByPrice(pageable, value, generalManagerId))
                    .orElse(Page.empty(pageable))
                    .map(ProductResponseDTO::new);
            case "measure" -> parseNumberDouble(search)
                    .map(value -> repository.findSearchProductsByMeasure(pageable, value, generalManagerId))
                    .orElse(Page.empty(pageable))
                    .map(ProductResponseDTO::new);
            case "quantity" -> parseNumberInt(search)
                    .map(value -> repository.findSearchProductsByQuantity(pageable, value, generalManagerId))
                    .orElse(Page.empty())
                    .map(ProductResponseDTO::new);
            case "description" -> repository
                    .findSearchProductsByDescription(pageable, search, generalManagerId)
                    .map(ProductResponseDTO::new);
            case "unitValue" -> repository
                    .findSearchProductsByUnitValue(pageable, search, generalManagerId)
                    .map(ProductResponseDTO::new);
            default -> throw new ProductException("Parâmetro não aceito para a pesquisa");
        };
    }

    private Optional<Double> parseNumberDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(Double.parseDouble(value.trim().replace(',', '.')));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }


    private Optional<Integer> parseNumberInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(value.trim().replace(',', '.')));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    public List<ProductResponseDTO> getAvailable(UUID generalManagerId) {
        return repository
                .findActiveProducts(generalManagerId)
                .stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    public ProductResponseDTO getById(UUID id) {
        ProductEntity product = findById(id);
        return new ProductResponseDTO(product);
    }

    public ProductEntity getProductEntityById(UUID id) {
        return findById(id);
    }

    public ProductResponseDTO post(@Valid ProductRequestDTO data, UUID generalManagerId) {
        ProductEntity product = new ProductEntity(data, generalManagerId);
        repository.save(product);
        return new ProductResponseDTO(product);
    }

//    public List<Object[]> periodOfCreation(PeriodTimeRequestDTO periodTimeDTO) {
//        return repository.periodTime(periodTimeDTO.from(), periodTimeDTO.to());
//    }

    @Transactional
    public void updateProductQuantity(UUID id, Integer actualQuantity) {
        ProductEntity product = this.findById(id);
        product.setQuantity(actualQuantity);
        this.repository.save(product);
    }

    @Transactional
    public ProductResponseDTO patch(@PathVariable UUID id, @RequestBody ProductRequestDTO data) {
        ProductEntity product = findById(id);

        product.setName(data.name());
        product.setPrice(data.price().toString());
        product.setQuantity(Integer.parseInt(data.quantity()));
        product.setMeasure(Double.parseDouble(data.measure()));
        product.setUnitValue(data.unitValue());
        product.setDescription(data.description());
        repository.save(product);

        return new ProductResponseDTO(product);
    }

    @Transactional
    public void receiveReturnedProducts(UUID id, Integer quantity) {
        ProductEntity product = this.findById(id);
        product.setQuantity(product.getQuantity() + quantity);
        this.repository.save(product);
    }

    @Transactional
    public void inactivate(UUID id) {
        ProductEntity product = findById(id);

        if(product.getInactivatedDt() != null) {
            throw new ProductException("Veículo já inativo");
        }

        product.setInactivatedDt(LocalDate.now());
        repository.save(product);
    }

    public List<ProductEntity> findAllByIds(List<UUID> productIds) {
        return this.repository.findAllById(productIds);
    }

    public void postImage(UUID id, MultipartFile image) {
        ProductEntity product = this.findById(id);
        product.setImageUrl(this.imageService.uploadImage(image));
        this.repository.save(product);
    }
}
