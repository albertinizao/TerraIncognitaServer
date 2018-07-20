package com.opipo.terraincognitaserver.service;

import java.util.Collection;

import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.Payment;

public interface EventPaymentService {
    void createPayment(EventInscription inscription);

    Collection<Payment> getPayments(EventInscription eventInscription);
}
