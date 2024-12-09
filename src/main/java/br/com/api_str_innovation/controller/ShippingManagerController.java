package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.employee.ResponseDTO;
import br.com.api_str_innovation.dto.employee.shipping_manager.ShippingManagerRequestDTO;
import br.com.api_str_innovation.dto.employee.shipping_manager.ShippingManagerResponseDTO;
import br.com.api_str_innovation.entities.employee.ShippingManagerEntity;
import br.com.api_str_innovation.service.ShippingManagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shipping-manager")
public class ShippingManagerController {

    @Autowired
    private ShippingManagerService service;

    @GetMapping
    public ResponseEntity<List<ShippingManagerResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count());
    }


    @GetMapping(value = "/page")
    public ResponseEntity<Page<ShippingManagerResponseDTO>> getPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingManagerEntity> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody ShippingManagerRequestDTO data) {
        System.out.println(data);
        this.service.post(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingManagerResponseDTO> put(@PathVariable UUID id, @RequestBody ShippingManagerRequestDTO data) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.put(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        // There is an error when the message show "Inativado com sucesso", but the driver wasn`t inactivated.
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Inativado com sucesso"));
    }
}