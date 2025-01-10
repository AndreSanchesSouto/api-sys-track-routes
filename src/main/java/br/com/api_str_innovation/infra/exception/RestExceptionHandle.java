package br.com.api_str_innovation.infra.exception;

import br.com.api_str_innovation.exceptions.UserRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserRegisteredException.class)
    private ResponseEntity<String> userRegisteredHandle(UserRegisteredException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Teste");
    }

}
