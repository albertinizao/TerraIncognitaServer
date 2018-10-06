package com.opipo.terraincognitaserver.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.EventInscriptionId;
import com.opipo.terraincognitaserver.dto.InstalmentPrice;
import com.opipo.terraincognitaserver.dto.Payment;
import com.opipo.terraincognitaserver.dto.Price;
import com.opipo.terraincognitaserver.service.EventPaymentService;
import com.opipo.terraincognitaserver.service.EventService;
import com.opipo.terraincognitaserver.service.PaymentService;

@Service
public class EventPaymentServiceImpl implements EventPaymentService {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EventService eventService;

    @Override
    public void createPayment(EventInscription inscription) {
        getPayments(inscription).forEach(paymentService::save);
    }

    @Override
    public Collection<Payment> getPayments(EventInscription eventInscription) {
        Collection<Payment> payments = new ArrayList<>();
        Integer payNumber = 2;
        Event event = eventService.find(eventInscription.getId().getEvent());
        Double totalPrice = calculateTotalPrice(event, eventInscription);
        payments.add(createFirstPayment(eventInscription.getId(), event.getPrice()));

        payments.addAll(event.getPrice().getInstalementPrices().stream()
                .map(f -> createPayment(eventInscription.getId(), f, totalPrice, payNumber))
                .collect(Collectors.toList()));

        return payments;
    }

    private Double calculateTotalPrice(Event event, EventInscription eventInscription) {
        Double primaryTotalPrice = event.getPrice().getTotalPrice() - event.getPrice().getInscriptionPrice();
        if (eventInscription.getPartner()) {
            primaryTotalPrice = primaryTotalPrice - event.getPrice().getPartnerDiscount();
        }
        if (eventInscription.getNpc()) {
            primaryTotalPrice = primaryTotalPrice - event.getPrice().getNpcDiscount();
        }
        return primaryTotalPrice;
    }

    private Payment createPayment(EventInscriptionId eventInscription, InstalmentPrice instalmentPrice,
            Double totalPrice, Integer payNumber) {
        Payment inscriptionPayment = fillPayment(eventInscription, payNumber);
        inscriptionPayment.setAmount(roundMoney(totalPrice / 100 * instalmentPrice.getPercent()));
        inscriptionPayment.setLastDate(instalmentPrice.getLastDate());
        return inscriptionPayment;
    }

    public Double roundMoney(Double original) {
        Double round = Math.round((original) * 100.0) / 100.0;
        return round >= original ? round : round + 0.01;
    }

    private Payment createFirstPayment(EventInscriptionId eventInscription, Price price) {
        Payment inscriptionPayment = fillPayment(eventInscription, 1);
        inscriptionPayment.setAmount(price.getInscriptionPrice());
        inscriptionPayment.setLastDate(price.getInscriptionLastDate());
        return inscriptionPayment;
    }

    private Payment fillPayment(EventInscriptionId eventInscription, Integer payNumber) {
        Payment inscriptionPayment = paymentService.create();
        inscriptionPayment.setDescription(new StringBuilder().append("Pago ").append(payNumber).append("º de ")
                .append(eventInscription.getEvent()).toString());
        inscriptionPayment.setEventId(eventInscription.getEvent());
        inscriptionPayment.setPaid(false);
        inscriptionPayment.setEventId(eventInscription.getEvent());
        return inscriptionPayment;
    }

}
