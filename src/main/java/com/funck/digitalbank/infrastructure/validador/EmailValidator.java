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
        return unique ? !pessoaRepository.existsByEmail(email) : true;
    }

}
