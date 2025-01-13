package br.com.api_str_innovation.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UserRegisteredException extends RuntimeException {
    public UserRegisteredException(String message) {
        super(message);
    }

    public UserRegisteredException() {
        super("Testão");
    }

}
