package com.opipo.terraincognitaserver.validation.validator;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.opipo.terraincognitaserver.validation.constraint.DateBeforeTodayConstraint;

public class DateBeforeTodayValidator implements ConstraintValidator<DateBeforeTodayConstraint, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value == null || value < new Date().getTime();
    }

}
