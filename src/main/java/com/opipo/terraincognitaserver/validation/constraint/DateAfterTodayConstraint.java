package com.opipo.terraincognitaserver.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.opipo.terraincognitaserver.validation.validator.DateAfterTodayValidator;

@Documented
@Constraint(validatedBy = DateAfterTodayValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAfterTodayConstraint {
    String message() default "The date must be after today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
