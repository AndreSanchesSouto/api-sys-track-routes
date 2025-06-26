package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.client.ClientResponseDTO;
import br.com.api_str_innovation.dto.product.ProductRequestDTO;
import br.com.api_str_innovation.dto.product.ProductResponseDTO;
import br.com.api_str_innovation.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ProductResponseDTO>> getPaged(
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable, generalManagerId));
    }

    @GetMapping(value = "/search/{attribute}")
    public ResponseEntity<Page<ProductResponseDTO>> searchByAttribute(
            Pageable pageable,
            @PathVariable String attribute,
            @RequestParam String value,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getSearched(pageable, attribute, value, generalManagerId));
    }

    @GetMapping("/available")
    public ResponseEntity<List<ProductResponseDTO>> getAvailable(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAvailable(generalManagerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> post(
            @Valid @RequestBody ProductRequestDTO data,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.post(data, generalManagerId));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductResponseDTO> patch(@PathVariable UUID id, @Valid @RequestBody ProductRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.patch(id, data));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count(generalManagerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        this.service.inactivate(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }
}
