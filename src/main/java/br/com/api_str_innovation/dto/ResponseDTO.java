package br.com.api_str_innovation.dto;

public class ResponseDTO {

    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }

    // Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
