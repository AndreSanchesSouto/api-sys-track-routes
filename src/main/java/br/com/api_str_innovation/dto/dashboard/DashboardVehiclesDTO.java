package br.com.api_str_innovation.dto.dashboard;

public record DashboardVehiclesDTO(
        int waiting,
        int active,
        int unavailable,
        int on_use,
        int inactive,
        int on_road
) {}
