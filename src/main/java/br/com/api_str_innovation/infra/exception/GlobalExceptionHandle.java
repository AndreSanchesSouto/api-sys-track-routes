package br.com.api_str_innovation.infra.exception;

import br.com.api_str_innovation.exceptions.DataAddressException;
import br.com.api_str_innovation.exceptions.TokenException;
import br.com.api_str_innovation.exceptions.UserException;
import br.com.api_str_innovation.exceptions.VehicleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {

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

//    @ExceptionHandler(NullPointerException.class)
//    private ResponseEntity<ExceptionMessage> exceptionNullPointerHandle(NullPointerException exception) {
//        ExceptionMessage response = new ExceptionMessage(
//                HttpStatus.FORBIDDEN.value(),
//                HttpStatus.FORBIDDEN,
//                "Usuário não encontrado"
//        );
//        return ResponseEntity.
//                status(response.getStatus()).
//                body(response);
//    }

}
