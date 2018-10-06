package com.opipo.terraincognitaserver.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.opipo.terraincognitaserver.service.impl.EventPaymentServiceImpl;

public class EventPaymentServiceTest {
    private EventPaymentServiceImpl eventPayment = new EventPaymentServiceImpl();

    @Test
    public void givenDoubleThenRoundUp() {
        Assertions.assertAll("All the rounds up", () -> assertRound(2.0001, 2.01), () -> assertRound(2.0201, 2.03),
                () -> assertRound(2.0250, 2.03), () -> assertRound(2.0251, 2.03), () -> assertRound(2.0200, 2.02),
                () -> assertRound(2.0299, 2.03), () -> assertRound(2.02, 2.02));
    }

    private void assertRound(Double original, Double expected) {
        assertEquals(expected, eventPayment.roundMoney(original));
    }
}
