package br.com.api_str_innovation.infrastructure.exception;

import br.com.api_str_innovation.exceptions.DataAddressException;
import br.com.api_str_innovation.exceptions.TokenException;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.exceptions.VehicleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(UserException.class)
    private ResponseEntity<ExceptionMessage> userRegisteredHandle(UserException exception) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(VehicleException.class)
    private ResponseEntity<ExceptionMessage> vehicleRegisteredHandle(VehicleException exception) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<ExceptionMessage> usernameNotFoundedHandle(UsernameNotFoundException exception) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(TokenException.class)
    private ResponseEntity<ExceptionMessage> tokenExpiredExeption(TokenException exception) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED,
                exception.getMessage()
        );
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(DataAddressException.class)
    private ResponseEntity<ExceptionMessage> addressException(DataAddressException exception) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionMessage> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Erro de validação";

        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                errorMessage
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    private ResponseEntity<ExceptionMessage> handleHttpMediaTypeException(HttpMediaTypeException ex) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    private ResponseEntity<ExceptionMessage> exceptionMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    private ResponseEntity<ExceptionMessage> exceptionMissingRequestHeaderException(MissingRequestHeaderException exception) {
        ExceptionMessage response = new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
