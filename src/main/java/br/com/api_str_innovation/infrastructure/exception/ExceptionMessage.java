package br.com.api_str_innovation.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionMessage {
    private int error;
    private HttpStatus status;
    private String message;
}
