package com.funck.digitalbank.infrastructure.validador;

import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;

public class CustomPasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(List.of(
                new LengthRule(8, 8),
                new UppercaseCharacterRule(1),
                new SpecialCharacterRule(1)));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        String violations = validator.getMessages(result).stream().collect(Collectors.joining(","));

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(violations).addConstraintViolation();

        return false;
    }

}
