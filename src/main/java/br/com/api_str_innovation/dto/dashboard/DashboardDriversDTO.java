package br.com.api_str_innovation.dto.dashboard;

public record DashboardDriversDTO(
        int active,
        int unavailable,
        int inactive
) {}
