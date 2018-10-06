package com.opipo.terraincognitaserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.terraincognitaserver.dto.Payment;
import com.opipo.terraincognitaserver.service.PaymentService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/payment")
@Api(value = "REST API to manage payments")
public class PaymentController extends AbstractCRUDController<Payment, Long> {

    @Autowired
    private PaymentService service;

    @Override
    protected ServiceDTOInterface<Payment, Long> getService() {
        return service;
    }

    @Override
    protected Long getIdFromElement(Payment element) {
        return element.getId();
    }

}
