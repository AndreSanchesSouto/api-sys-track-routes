package br.com.api_str_innovation.infra.validation;

import br.com.api_str_innovation.infra.anotation.CnpjCpfAnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.CNPJValidator;
public class CnpjCpfValidator implements ConstraintValidator<CnpjCpfAnotation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) return false;

        String document = value.replaceAll("\\D", "");

        CPFValidator cpfValidator = new CPFValidator();
        CNPJValidator cnpjValidator = new CNPJValidator();

        return cpfValidator.isEligible(document) || cnpjValidator.isEligible(document);
    }

}
