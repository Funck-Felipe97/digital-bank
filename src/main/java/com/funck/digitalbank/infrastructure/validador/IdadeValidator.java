package com.funck.digitalbank.infrastructure.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IdadeValidator implements ConstraintValidator<Idade, LocalDate> {

    private int idadeMinima;
    private int idadeMaxima;

    @Override
    public void initialize(Idade constraintAnnotation) {
        idadeMaxima = constraintAnnotation.maxima();
        idadeMinima = constraintAnnotation.minima();
    }

    @Override
    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        Long idade = ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());
        return idade >= idadeMinima && idade <= idadeMaxima;
    }

}
