package com.opipo.terraincognitaserver.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.opipo.terraincognitaserver.validation.validator.NIFValidator;

@Documented
@Constraint(validatedBy = NIFValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NIFConstraint {
    String message() default "The NIF is incorect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
