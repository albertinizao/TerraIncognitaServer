package com.opipo.terraincognitaserver.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.opipo.terraincognitaserver.validation.validator.DateBeforeTodayValidator;

@Documented
@Constraint(validatedBy = DateBeforeTodayValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateBeforeTodayConstraint {
    String message() default "The date must be before today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
