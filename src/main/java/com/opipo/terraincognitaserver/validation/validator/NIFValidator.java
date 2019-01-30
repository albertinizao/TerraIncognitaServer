package com.opipo.terraincognitaserver.validation.validator;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.client.RestTemplate;

import com.opipo.terraincognitaserver.validation.constraint.DateBeforeTodayConstraint;

public class NIFValidator implements ConstraintValidator<DateBeforeTodayConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Boolean response = false;
        if (value != null && value.matches("^([0-9]{8}[A-Z])|[XYZ][0-9]{7}[A-Z]$")) {
            RestTemplate restTemplate = new RestTemplate();
            StringBuilder sb = new StringBuilder("http://letradni.appspot.com/api/");
            sb.append(value.substring(0, value.length() - 1));
            Map<String, String> quote = restTemplate.getForObject(sb.toString(), Map.class);
            response = value.equalsIgnoreCase(quote.get("dninie"));
        }
        return response;
    }

}
