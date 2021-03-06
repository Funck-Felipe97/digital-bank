package com.funck.digitalbank.infrastructure.validador;

import com.funck.digitalbank.domain.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Autowired
    private PessoaRepository pessoaRepository;

    private boolean unique = false;

    @Override
    public void initialize(Email constraintAnnotation) {
        unique = constraintAnnotation.unique();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        var isValid = unique ? !pessoaRepository.existsByEmail(email) : true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Já existe uma pessoa cadastrada com este email: ").addConstraintViolation();

        return isValid;
    }

}
