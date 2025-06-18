package br.com.api_str_innovation.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserChangePasswordDTO (
        @NotBlank(message = "Você precisa informar uma senha")
        String newEmployeePassword,
        @NotBlank(message = "Você precisa informar a confirmação de senha")
        String newEmployeePasswordConfirmation,
        @NotBlank(message = "Você precisa informar a sua senha")
        String userPassword
){}
