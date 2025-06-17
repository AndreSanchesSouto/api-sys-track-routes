package br.com.api_str_innovation.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserChangePasswordDTO (
        @NotNull(message = "Você precisa informar uma senha")
        String newEmployeePassword,
        @NotNull(message = "Você precisa informar a confirmação de senha")
        String newEmployeePasswordConfirmation,
        @NotNull(message = "Você precisa informar a sua senha")
        String generalManagerPassword
){}
