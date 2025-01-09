package br.com.api_str_innovation.controller;

import br.com.api_str_innovation.dto.employee.UserRequestDTO;
import br.com.api_str_innovation.dto.employee.UserResponseDTO;
import br.com.api_str_innovation.dto.period_time.PeriodTimeRequestDTO;
import br.com.api_str_innovation.entities.employee.UserEntity;
import br.com.api_str_innovation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/driver")
public class DriverController {



}