package com.opipo.terraincognitaserver.validation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.opipo.terraincognitaserver.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NIFValidatorTest {

    @InjectMocks
    private NIFValidator nifValidator;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(nifValidator);
    }

    @ParameterizedTest
    @DisplayName("Valid DNI")
    @ValueSource(strings = {"96710979G", "91320498X", "41202050B", "04516720A"})
    public void givenValidDNIThenCheckOk(String dni) {
        Boolean response = nifValidator.isValid(dni, null);
        assertTrue(response);
    }

    @ParameterizedTest
    @DisplayName("Invalid DNI")
    @ValueSource(strings = {"96710970G", "91320498A", "41202050", "4516720A", ""})
    public void givenInvalidDNIThenCheckKo(String dni) {
        Boolean response = nifValidator.isValid(dni, null);
        assertFalse(response);
    }

    @ParameterizedTest
    @DisplayName("Valid NIE")
    @ValueSource(strings = {"Y0398073A", "X0264711G", "Z5464948S", "Y0611262M"})
    public void givenValidNIEThenCheckOk(String dni) {
        Boolean response = nifValidator.isValid(dni, null);
        assertTrue(response);
    }

    @ParameterizedTest
    @DisplayName("Invalid NIE")
    @ValueSource(strings = {"Z1280951G", "Z0048845Q", "X9306300P", ""})
    public void givenInvalidNIEThenCheckKo(String dni) {
        Boolean response = nifValidator.isValid(dni, null);
        assertFalse(response);
    }

    @Test
    @DisplayName("Null DNI")
    public void givenNullDNIThenCheckKo() {
        Boolean response = nifValidator.isValid((String) null, null);
        assertFalse(response);
    }

}
