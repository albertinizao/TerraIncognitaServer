package com.opipo.terraincognitaserver.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.opipo.terraincognitaserver.MockitoExtension;
import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.EventInscriptionId;
import com.opipo.terraincognitaserver.dto.InstalmentPrice;
import com.opipo.terraincognitaserver.dto.Payment;
import com.opipo.terraincognitaserver.dto.Price;
import com.opipo.terraincognitaserver.service.impl.EventPaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EventPaymentServiceTest {
    @InjectMocks
    private EventPaymentServiceImpl eventPayment;
    @Spy
    private PaymentService paymentService;
    @Mock
    private EventService eventService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(eventPayment);
    }

    @DisplayName("All the rounds are well")
    @Test
    public void givenDoubleThenRoundUp() {
        Assertions.assertAll("All the rounds up", () -> assertRound(2.0001, 2.01), () -> assertRound(2.0201, 2.03),
                () -> assertRound(2.0250, 2.03), () -> assertRound(2.0251, 2.03), () -> assertRound(2.0200, 2.02),
                () -> assertRound(2.0299, 2.03), () -> assertRound(2.02, 2.02));
    }

    private void assertRound(Double original, Double expected) {
        assertEquals(expected, eventPayment.roundMoney(original));
    }

    @ParameterizedTest(name = "{index} => eventId={0}, paymentNumber={1}")
    @DisplayName("Create payment and fill it")
    @CsvSource({"MiEvento, 1", "Vientos de Invierno, 12", ",0"})
    public void givenInscriptionAndPaymentNumberThenFillPayment(String eventId, Integer paymentNumber) {
        EventInscriptionId eventInscription = Mockito.mock(EventInscriptionId.class);
        Mockito.when(eventInscription.getEvent()).thenReturn(eventId);
        Payment expected = assertPaymentCreation(eventInscription, paymentNumber);
        Payment actual = eventPayment.fillPayment(eventInscription, paymentNumber);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{index} => amount={0}, lastDate={1}")
    @DisplayName("Create first payment")
    @CsvSource({"5.2, 46", "2.3, 2108505600", "0, 0", "365891, 1548243329"})
    public void givenEventInscriptionAndPriceThenCreateFirstPayment(Double amount, Long lastDate) {
        EventInscriptionId eventInscription = Mockito.mock(EventInscriptionId.class);
        Price price = Mockito.mock(Price.class);
        Payment expected = assertPaymentCreation(eventInscription, 1);

        Mockito.when(price.getInscriptionPrice()).thenReturn(amount);
        Mockito.when(price.getInscriptionLastDate()).thenReturn(lastDate);

        expected.setAmount(amount);
        expected.setLastDate(lastDate);

        Payment actual = eventPayment.createFirstPayment(eventInscription, price);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{index} => total={0}, lastDate={1}, percent={2}, payNumber={3}")
    @DisplayName("Create other payment")
    @CsvSource({"75, 1548243329, 100, 2", "70, 2108505600, 50, 1", "71, 2000000000, 25, 3"})
    public void givenEventInscriptionAndPriceThenCreateOtherPayment(Double total, Long lastDate, Double percent,
            Integer payNumber) {
        EventInscriptionId eventInscription = Mockito.mock(EventInscriptionId.class);
        InstalmentPrice instalmentPrice = Mockito.mock(InstalmentPrice.class);
        Mockito.when(instalmentPrice.getPercent()).thenReturn(percent);
        Mockito.when(instalmentPrice.getLastDate()).thenReturn(lastDate);
        Payment expected = assertPaymentCreation(eventInscription, payNumber);

        Double amount = eventPayment.roundMoney(total / 100 * percent);

        expected.setAmount(amount);
        expected.setLastDate(lastDate);

        Payment actual = eventPayment.createPayment(eventInscription, instalmentPrice, total, payNumber);
        assertEquals(expected, actual);
    }

    public Payment assertPaymentCreation(EventInscriptionId eventInscription, Integer payNumber) {

        Mockito.when(paymentService.create()).thenReturn(new Payment()).thenReturn(new Payment())
                .thenReturn(new Payment()).thenReturn(new Payment()).thenReturn(new Payment());

        String description = new StringBuilder().append("Pago ").append(payNumber).append("º de ")
                .append(eventInscription.getEvent()).toString();

        Payment expected = new Payment();
        expected.setEventId(eventInscription.getEvent());
        expected.setPaid(false);
        expected.setDescription(description);

        return expected;

    }

    @ParameterizedTest(name = "{index} => total={0}, lastDate={1}, percent={2}, payNumber={3}, expected={4}")
    @DisplayName("Calculate real price")
    @CsvSource({"75, 35, 5, 5, false, false, 40", "80, 40, 10, 20, true, false, 30", "70, 15, 30, 40, false, true, 15",
            "60, 30, 5, 7, true, true, 18"})
    public void givenEventAndInscriptionThenGetPayment(Double totalPrice, Double inscriptionPrice,
            Double partnerDiscount, Double npcDiscount, boolean isPartner, boolean isNpc, Double expected) {
        Price price = Mockito.mock(Price.class);
        Mockito.when(price.getInscriptionPrice()).thenReturn(inscriptionPrice);
        Mockito.when(price.getTotalPrice()).thenReturn(totalPrice);
        Mockito.when(price.getPartnerDiscount()).thenReturn(partnerDiscount);
        Mockito.when(price.getNpcDiscount()).thenReturn(npcDiscount);
        Event event = Mockito.mock(Event.class);
        Mockito.when(event.getPrice()).thenReturn(price);
        EventInscription eventInscription = Mockito.mock(EventInscription.class);
        Mockito.when(eventInscription.getNpc()).thenReturn(isNpc);
        Mockito.when(eventInscription.getPartner()).thenReturn(isPartner);
        Double actual = eventPayment.calculateTotalPrice(event, eventInscription);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{index} => totalPrice={0}, percent1={1}, percent2={2}, lastDate1={3}, lastDate2={4}, amountExpected1={5}, amountExpected2={6}")
    @DisplayName("Calculate real price")
    @CsvSource({"20, 25, 50, 123500, 1234564, 5, 10", "20, 25, 50, 1234564, 123500, 10, 5"})
    public void givenEventEventInscriptionAndTotalPriceThenGetNonFirstPayment(Double totalPrice, Double percent1,
            Double percent2, Long lastDate1, Long lastDate2, Double amountExpected1, Double amountExpected2) {
        Event event = Mockito.mock(Event.class);
        EventInscription eventInscription = Mockito.mock(EventInscription.class);
        Price price = Mockito.mock(Price.class);
        InstalmentPrice instalmentPrice1 = new InstalmentPrice();
        instalmentPrice1.setPercent(percent1);
        instalmentPrice1.setLastDate(lastDate1);
        InstalmentPrice instalmentPrice2 = new InstalmentPrice();
        instalmentPrice2.setPercent(percent2);
        instalmentPrice2.setLastDate(lastDate2);
        Collection<InstalmentPrice> instalmentPrices = Arrays.asList(instalmentPrice1, instalmentPrice2);
        Mockito.when(price.getInstalementPrices()).thenReturn(instalmentPrices);
        Mockito.when(event.getPrice()).thenReturn(price);
        EventInscriptionId eventInscriptionId = Mockito.mock(EventInscriptionId.class);
        String eventInscriptionIdstring = "myId";
        Mockito.when(eventInscriptionId.getEvent()).thenReturn(eventInscriptionIdstring);
        Mockito.when(eventInscription.getId()).thenReturn(eventInscriptionId);
        Collection<Payment> expected = new ArrayList<>();
        Payment p1 = assertPaymentCreation(eventInscriptionId, 2);
        p1.setAmount(amountExpected1);
        p1.setLastDate(lastDate1 < lastDate2 ? lastDate1 : lastDate2);
        expected.add(p1);
        Payment p2 = assertPaymentCreation(eventInscriptionId, 3);
        p2.setAmount(amountExpected2);
        p2.setLastDate(lastDate1 > lastDate2 ? lastDate1 : lastDate2);
        expected.add(p2);

        Collection<Payment> actual = eventPayment.getNonFirstPayments(event, eventInscription, totalPrice);
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));

    }

    @ParameterizedTest(name = "{index} => totalPrice={0}, percent1={1}, percent2={2}, lastDate1={3}, lastDate2={4}, amountExpected1={5}, amountExpected2={6}, inscriptionPrice={7}, inscriptionDate= {8} ")
    @DisplayName("Get all payments")
    @CsvSource({"70, 25, 50, 123500, 1234564, 5, 10, 50, 123411", "60, 25, 50, 1234564, 123500, 10, 5, 40, 123411"})
    public void givenEventEventInscriptionAndTotalPriceThenGetPayments(Double totalPrice, Double percent1,
            Double percent2, Long lastDate1, Long lastDate2, Double amountExpected1, Double amountExpected2,
            Double inscriptionPrice, Long inscriptionDate) {
        Event event = Mockito.mock(Event.class);
        EventInscription eventInscription = Mockito.mock(EventInscription.class);
        Price price = Mockito.mock(Price.class);
        InstalmentPrice instalmentPrice1 = new InstalmentPrice();
        instalmentPrice1.setPercent(percent1);
        instalmentPrice1.setLastDate(lastDate1);
        InstalmentPrice instalmentPrice2 = new InstalmentPrice();
        instalmentPrice2.setPercent(percent2);
        instalmentPrice2.setLastDate(lastDate2);
        Collection<InstalmentPrice> instalmentPrices = Arrays.asList(instalmentPrice1, instalmentPrice2);
        Mockito.when(price.getInstalementPrices()).thenReturn(instalmentPrices);
        Mockito.when(price.getInscriptionPrice()).thenReturn(inscriptionPrice);
        Mockito.when(price.getTotalPrice()).thenReturn(totalPrice);
        Mockito.when(price.getInscriptionLastDate()).thenReturn(inscriptionDate);
        Mockito.when(event.getPrice()).thenReturn(price);
        EventInscriptionId eventInscriptionId = Mockito.mock(EventInscriptionId.class);
        String eventInscriptionIdstring = "myId";
        Mockito.when(eventInscriptionId.getEvent()).thenReturn(eventInscriptionIdstring);
        Mockito.when(eventInscription.getId()).thenReturn(eventInscriptionId);
        Mockito.when(eventService.find(eventInscriptionIdstring)).thenReturn(event);
        Collection<Payment> expected = new ArrayList<>();
        Payment p0 = assertPaymentCreation(eventInscriptionId, 1);
        p0.setAmount(inscriptionPrice);
        p0.setLastDate(inscriptionDate);
        expected.add(p0);
        Payment p1 = assertPaymentCreation(eventInscriptionId, 2);
        p1.setAmount(amountExpected1);
        p1.setLastDate(lastDate1 < lastDate2 ? lastDate1 : lastDate2);
        expected.add(p1);
        Payment p2 = assertPaymentCreation(eventInscriptionId, 3);
        p2.setAmount(amountExpected2);
        p2.setLastDate(lastDate1 > lastDate2 ? lastDate1 : lastDate2);
        expected.add(p2);

        Collection<Payment> actual = eventPayment.getPayments(eventInscription);
        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));

    }

    @ParameterizedTest(name = "{index} => totalPrice={0}, percent1={1}, percent2={2}, lastDate1={3}, lastDate2={4}, amountExpected1={5}, amountExpected2={6}, inscriptionPrice={7}, inscriptionDate= {8} ")
    @DisplayName("Get and save payments")
    @CsvSource({"70, 25, 50, 123500, 1234564, 5, 10, 50, 123411", "60, 25, 50, 1234564, 123500, 10, 5, 40, 123411"})
    public void givenEventEventInscriptionAndTotalPriceThenSavePayments(Double totalPrice, Double percent1,
            Double percent2, Long lastDate1, Long lastDate2, Double amountExpected1, Double amountExpected2,
            Double inscriptionPrice, Long inscriptionDate) {
        Event event = Mockito.mock(Event.class);
        EventInscription eventInscription = Mockito.mock(EventInscription.class);
        Price price = Mockito.mock(Price.class);
        InstalmentPrice instalmentPrice1 = new InstalmentPrice();
        instalmentPrice1.setPercent(percent1);
        instalmentPrice1.setLastDate(lastDate1);
        InstalmentPrice instalmentPrice2 = new InstalmentPrice();
        instalmentPrice2.setPercent(percent2);
        instalmentPrice2.setLastDate(lastDate2);
        Collection<InstalmentPrice> instalmentPrices = Arrays.asList(instalmentPrice1, instalmentPrice2);
        Mockito.when(price.getInstalementPrices()).thenReturn(instalmentPrices);
        Mockito.when(price.getInscriptionPrice()).thenReturn(inscriptionPrice);
        Mockito.when(price.getTotalPrice()).thenReturn(totalPrice);
        Mockito.when(price.getInscriptionLastDate()).thenReturn(inscriptionDate);
        Mockito.when(event.getPrice()).thenReturn(price);
        EventInscriptionId eventInscriptionId = Mockito.mock(EventInscriptionId.class);
        String eventInscriptionIdstring = "myId";
        Mockito.when(eventInscriptionId.getEvent()).thenReturn(eventInscriptionIdstring);
        Mockito.when(eventInscription.getId()).thenReturn(eventInscriptionId);
        Mockito.when(eventService.find(eventInscriptionIdstring)).thenReturn(event);
        Collection<Payment> expected = new ArrayList<>();
        Payment p0 = assertPaymentCreation(eventInscriptionId, 1);
        p0.setAmount(inscriptionPrice);
        p0.setLastDate(inscriptionDate);
        expected.add(p0);
        Payment p1 = assertPaymentCreation(eventInscriptionId, 2);
        p1.setAmount(amountExpected1);
        p1.setLastDate(lastDate1 < lastDate2 ? lastDate1 : lastDate2);
        expected.add(p1);
        Payment p2 = assertPaymentCreation(eventInscriptionId, 3);
        p2.setAmount(amountExpected2);
        p2.setLastDate(lastDate1 > lastDate2 ? lastDate1 : lastDate2);
        expected.add(p2);


        eventPayment.createPayment(eventInscription);
        
        ArgumentCaptor<Payment> paymentsCaptured = ArgumentCaptor.forClass(Payment.class);
        Mockito.verify(paymentService, Mockito.times(3)).save(paymentsCaptured.capture());
        Collection<Payment> payments = paymentsCaptured.getAllValues();
        assertTrue(payments.containsAll(expected));
    }
}
