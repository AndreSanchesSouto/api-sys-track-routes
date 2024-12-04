package br.com.api_str_innovation.dto.employee;

import lombok.Getter;
import lombok.Setter;

public class ResponseDTO {

    @Getter
    @Setter
    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }

}
