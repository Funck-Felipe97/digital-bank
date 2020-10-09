package com.funck.digitalbank.infrastructure.validador;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdadeValidator.class)
@Documented
public @interface Idade {

    String message() default "Idade inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int minima() default 0;
    int maxima() default 1000;

}
