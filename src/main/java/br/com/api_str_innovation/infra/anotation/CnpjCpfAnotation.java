package br.com.api_str_innovation.infra.anotation;

import br.com.api_str_innovation.infra.validation.CnpjCpfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CnpjCpfValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CnpjCpfAnotation {
    String message() default "documento inválido!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
