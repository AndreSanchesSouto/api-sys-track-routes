package br.com.api_str_innovation.dto.dashboard;

public record DashboardDeliveryDTO(
        int waiting,
        int active,
        int on_road,
        int canceled,
        int confirmed,
        int comingBack
) {}

