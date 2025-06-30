package br.com.api_str_innovation.dto.user.report;

import java.util.List;

public record PeriodCreationResponseDTO(
        List<PeriodReportDTO> periodData,
        List<UserDetailDTO> userDetails
) {
    public record PeriodReportDTO(
            Integer year,
            Integer month,
            Long driverCount
    ) {}

    public record UserDetailDTO(
            String name,
            String login,
            String role,
            Integer year,
            Integer month
    ) {}
}