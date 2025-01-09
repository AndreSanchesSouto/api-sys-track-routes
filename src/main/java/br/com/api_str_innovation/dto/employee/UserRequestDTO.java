package br.com.api_str_innovation.dto.employee;

import br.com.api_str_innovation.entities.employee.Role;

public record UserRequestDTO(
            String name,
            String email,
            String login,
            String password,
            String status,
            Role role
) { }
