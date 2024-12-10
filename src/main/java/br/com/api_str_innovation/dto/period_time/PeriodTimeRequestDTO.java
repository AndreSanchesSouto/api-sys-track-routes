package br.com.api_str_innovation.dto.period_time;

import java.time.LocalDate;

public record PeriodTimeRequestDTO (LocalDate from, LocalDate to){
}
