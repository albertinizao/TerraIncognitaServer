package com.opipo.terraincognitaserver.service.impl;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.Payment;
import com.opipo.terraincognitaserver.repository.PaymentRepository;
import com.opipo.terraincognitaserver.repository.SequenceRepository;
import com.opipo.terraincognitaserver.service.PaymentService;

@Service
public class PaymentServiceImpl extends AbstractServiceDTO<Payment, Long> implements PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Override
    protected MongoRepository<Payment, Long> getRepository() {
        return repository;
    }

    @Override
    protected Payment buildElement(Long id) {
        Payment element = new Payment();
        element.setId(id);
        return element;
    }

    @Override
    public Long buildId() {
        return sequenceRepository.getNextSequenceId(Payment.SEQUENCE);
    }

    @Override
    protected Consumer<Payment> preserveOldValues(Payment newValue) {
        return c -> {
        };
    }

    @Override
    protected Function<Payment, Long> getId() {
        return f -> f.getId();
    }

    @Override
    public Payment saveComplete(Payment payment) {
        validate(payment);
        return getRepository().save(payment);
    }

    @Override
    public Payment accept(Long id) {
        Payment payment = this.find(id);
        payment.setPaid(true);
        return getRepository().save(payment);
    }

    @Override
    public Payment create(Long id) {
        throw new UnsupportedOperationException(AbstractServiceDTO.AUTOGEN_ID);
    }

    @Override
    public Payment create() {
        return getRepository().save(buildElement(buildId()));
    }
}
