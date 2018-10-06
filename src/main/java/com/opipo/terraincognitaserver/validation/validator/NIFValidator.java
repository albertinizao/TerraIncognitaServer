package com.opipo.terraincognitaserver.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.aeat.valida.Validador;
import com.opipo.terraincognitaserver.validation.constraint.DateBeforeTodayConstraint;

public class NIFValidator implements ConstraintValidator<DateBeforeTodayConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || new Validador().checkNif(value) > 0;
    }

}
