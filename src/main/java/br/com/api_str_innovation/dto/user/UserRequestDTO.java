package br.com.api_str_innovation.dto.user;

import br.com.api_str_innovation.entities.user.Role;
import br.com.api_str_innovation.infrastructure.anotation.CnpjCpfAnotation;
import jakarta.annotation.Nullable;
import br.com.api_str_innovation.entities.user.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public record UserRequestDTO(
            @NotBlank(message = "Informe um nome")
            String              name,
            MultipartFile       image,
            @NotBlank
            @Email
            String              email,
            @Nullable
            @CnpjCpfAnotation
            String              document,
            @NotBlank
            String              login,
            @NotBlank
            String              password,
            String              confirmPassword,
            UserStatus          status,
            @NotNull
            Role                role
) {
    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", document='" + document + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", role=" + role +
                '}';
    }

}
