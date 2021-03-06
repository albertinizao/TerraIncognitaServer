package com.opipo.terraincognitaserver.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Payment autogenerated")
public class PaymentTest {

    private Payment payment;

    @BeforeEach
    public void init() {
        payment = new Payment();
    }

    @Test
    @DisplayName("The getter and the setter of id work well")
    public void idAttributeTest() {
        Long id = Long.valueOf(1L);
        payment.setId(id);
        assertEquals(id, payment.getId());
    }

    @Test
    @DisplayName("The getter and the setter of userId work well")
    public void userIdAttributeTest() {
        String userId = Integer.toString(1);
        payment.setUserId(userId);
        assertEquals(userId, payment.getUserId());
    }

    @Test
    @DisplayName("The getter and the setter of eventId work well")
    public void eventIdAttributeTest() {
        String eventId = Integer.toString(2);
        payment.setEventId(eventId);
        assertEquals(eventId, payment.getEventId());
    }

    @Test
    @DisplayName("The getter and the setter of description work well")
    public void descriptionAttributeTest() {
        String description = Integer.toString(3);
        payment.setDescription(description);
        assertEquals(description, payment.getDescription());
    }

    @Test
    @DisplayName("The getter and the setter of amount work well")
    public void amountAttributeTest() {
        Double amount = Double.valueOf(4D);
        payment.setAmount(amount);
        assertEquals(amount, payment.getAmount());
    }

    @Test
    @DisplayName("The getter and the setter of paid work well")
    public void paidAttributeTest() {
        Boolean paid = Boolean.TRUE;
        payment.setPaid(paid);
        assertEquals(paid, payment.getPaid());
    }

    @Test
    @DisplayName("The getter and the setter of lastDate work well")
    public void lastDateAttributeTest() {
        Long lastDate = Long.valueOf(1L);
        payment.setLastDate(lastDate);
        assertEquals(lastDate, payment.getLastDate());
    }

    @Test
    public void givenSameObjReturnThatTheyAreEquals() {
        Payment o1 = new Payment();
        Payment o2 = new Payment();
        assertEquals(o1, o2);
    }

    @Test
    public void givenSameObjReturnZero() {
        Payment o1 = new Payment();
        Payment o2 = new Payment();
        assertEquals(0, o1.compareTo(o2));
    }

    @Test
    public void givenObjectFromOtherClassReturnThatTheyArentEquals() {
        Payment o1 = new Payment();
        assertNotEquals(o1, "");
    }

    @Test
    public void givenSameObjReturnSameHashCode() {
        Payment o1 = new Payment();
        Payment o2 = new Payment();
        assertEquals(o1.hashCode(), o2.hashCode());
    }

}