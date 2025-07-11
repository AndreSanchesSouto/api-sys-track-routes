package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.dashboard.DashboardDriversDTO;
import br.com.api_str_innovation.dto.user.report.PeriodCreationResponseDTO;
import br.com.api_str_innovation.dto.user.UserChangePasswordDTO;
import br.com.api_str_innovation.dto.user.UserRequestDTO;
import br.com.api_str_innovation.dto.user.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.dto.user.update.UserUpdateRequestDTO;
import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.entities.user.UserEntity;
import br.com.api_str_innovation.entities.user.UserStatus;
import br.com.api_str_innovation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllDriversByGeneralManagerId(generalManagerId));
    }

    @GetMapping("employees")
    public ResponseEntity<List<UserEntity>> getAllEmployeesByGeneralManagerId(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllEmployeesByGeneralManagerId(generalManagerId));
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<UserResponseDTO>> getDrivers(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getDrivers(generalManagerId));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<UserResponseDTO>> getPaged(
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPaged(pageable, generalManagerId));
    }

    @GetMapping("/driver-status")
    public ResponseEntity<DashboardDriversDTO> getDriverStatus(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return this.service.getDriversStatus(generalManagerId);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Page<UserResponseDTO>> getDriversByStatus(
            @RequestParam String status,
            Pageable pageable,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getByStatus(status, pageable, generalManagerId));
    }

    @GetMapping(value = "/search/{attribute}")
    public ResponseEntity<Page<UserResponseDTO>> searchByAttribute(
            Pageable pageable,
            @PathVariable String attribute,
            @RequestParam String value,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(this.service.getSearched(pageable, attribute, value, generalManagerId));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count(@RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.count(generalManagerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> post(
            @RequestParam("name")String name,
            @RequestParam("image")MultipartFile image,
            @RequestParam("email")String email,
            @RequestParam("document")String document,
            @RequestParam("login")String login,
            @RequestParam("password")String password,
            @RequestParam("confirmPassword")String confirmPassword,
            @RequestParam("role") Role role,
            @RequestHeader("general-manager-id") UUID generalManagerId
    ) {
        UserRequestDTO dto = new UserRequestDTO(name, image, email, document, login, password, confirmPassword, UserStatus.ACTIVE, role);
        this.service.post(dto, generalManagerId);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("/period-of-creation")
    public ResponseEntity<PeriodCreationResponseDTO> periodOfCreation(
            @Valid @RequestBody PeriodTimeRequestDTO data,
            @RequestHeader("general-manager-id") UUID generalManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.periodOfCreation(data, generalManagerId));
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<Void> changeUserPassword(@PathVariable UUID id, @Valid @RequestBody UserChangePasswordDTO data) {
        return this.service.changeUserPassword(id, data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patch(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequestDTO data) {
        this.service.patch(id, data);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> inactivate(@PathVariable UUID id) {
        this.service.inactivate(id);
        return ResponseEntity.status(HttpStatus.OK).body("Inativado");
    }

}