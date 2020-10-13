package com.funck.digitalbank.infrastructure.validador;

import com.funck.digitalbank.domain.repositories.PessoaRepository;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Autowired
    private PessoaRepository pessoaRepository;

    private boolean unique = false;

    @Override
    public void initialize(CPF constraintAnnotation) {
        unique = constraintAnnotation.unique();
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        var isValid = unique ? !pessoaRepository.existsByCpf(cpf) : true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Já existe uma pessoa cadastrada com este CPF: ").addConstraintViolation();

        return isValid;
    }

}
