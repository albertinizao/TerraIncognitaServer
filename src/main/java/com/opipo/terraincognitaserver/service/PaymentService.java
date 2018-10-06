package com.opipo.terraincognitaserver.service;

import com.opipo.terraincognitaserver.dto.Payment;

public interface PaymentService extends ServiceDTOInterface<Payment, Long> {

    Payment accept(Long id);
}
