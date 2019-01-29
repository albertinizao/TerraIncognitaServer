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
public class DateBeforeTodayValidatorTest {

    private DateBeforeTodayValidator dateBeforeTodayValidator = new DateBeforeTodayValidator();

    @ParameterizedTest
    @DisplayName("Check day before correct")
    @ValueSource(longs = {0L, -5360761019000L, 1321009871000L})
    public void givenDateThenCheckIsAfterNow(long date) {
        assertTrue(dateBeforeTodayValidator.isValid(date, null));
    }

    @ParameterizedTest
    @DisplayName("Check day before incorrect")
    @ValueSource(longs = {32503683661000L, 1893459661000L, 2979787564000L})
    public void givenDateThenCheckIsBeforeNow(long date) {
        assertFalse(dateBeforeTodayValidator.isValid(date, null));
    }

    @ParameterizedTest(name = "{index} => toNow={0}, response={1}")
    @DisplayName("Check day near now")
    @CsvSource({"1000, false", "-1000, true", "-1, true"})
    public void givenDateThenCheckIsBeforeNow(String date, String response) {
        assertEquals(Boolean.valueOf(response),
                dateBeforeTodayValidator.isValid(new Date().getTime() + Long.valueOf(date), null));
    }

    @Test
    @DisplayName("Check with null date")
    public void givenNullDateThenCheckIsBeforeNow() {
        assertTrue(dateBeforeTodayValidator.isValid(null, null));
    }
}
