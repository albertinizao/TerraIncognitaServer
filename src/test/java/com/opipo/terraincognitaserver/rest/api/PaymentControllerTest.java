package com.opipo.terraincognitaserver.rest.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.opipo.terraincognitaserver.dto.Payment;
import com.opipo.terraincognitaserver.service.PaymentService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

public class PaymentControllerTest extends AbstractCRUDControllerTest<Payment, Long> {

    @InjectMocks
    private PaymentController controller;

    @Mock
    private PaymentService service;

    @Override
    protected AbstractCRUDController<Payment, Long> getController() {
        return controller;
    }

    @Override
    protected ServiceDTOInterface<Payment, Long> getService() {
        return service;
    }

    @Override
    protected Long getCorrectID() {
        return 42L;
    }

    @Override
    protected Long getIncorrectID() {
        return 32L;
    }

    @Override
    protected Payment buildElement(Long id) {
        Payment element = new Payment();
        element.setId(id);
        return element;
    }

}
