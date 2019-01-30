package com.opipo.terraincognitaserver.validation.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.opipo.terraincognitaserver.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateAfterTodayValidatorTest {

    private DateAfterTodayValidator dateAfterTodayValidator = new DateAfterTodayValidator();

    @ParameterizedTest
    @DisplayName("Check day after correct")
    @ValueSource(longs = {32503683661000L, 1893459661000L, 2979787564000L})
    public void givenDateThenCheckIsAfterNow(long date) {
        assertTrue(dateAfterTodayValidator.isValid(date, null));
    }

    @ParameterizedTest
    @DisplayName("Check day after incorrect")
    @ValueSource(longs = {0L, -5360761019000L, 1321009871000L})
    public void givenDateThenCheckIsBeforeNow(long date) {
        assertFalse(dateAfterTodayValidator.isValid(date, null));
    }

    @ParameterizedTest(name = "{index} => toNow={0}, response={1}")
    @DisplayName("Check day near now")
    @CsvSource({"1000, true", "-1000, false", "-1, false"})
    public void givenDateThenCheckIsBeforeNow(String date, String response) {
        assertEquals(Boolean.valueOf(response),
                dateAfterTodayValidator.isValid(new Date().getTime() + Long.valueOf(date), null));
    }

    @Test
    @DisplayName("Check with null date")
    public void givenNullDateThenCheckIsBeforeNow() {
        assertTrue(dateAfterTodayValidator.isValid(null, null));
    }
}
