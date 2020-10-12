package com.funck.digitalbank.infrastructure.validador;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Documented
@Constraint(validatedBy = CustomPasswordValidator.class)
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Senha inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
