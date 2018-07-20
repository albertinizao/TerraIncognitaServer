package com.opipo.terraincognitaserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Payment;
import com.opipo.terraincognitaserver.repository.PaymentRepository;
import com.opipo.terraincognitaserver.repository.SequenceRepository;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;
import com.opipo.terraincognitaserver.service.impl.PaymentServiceImpl;

public class PaymentServiceTest extends GenericCRUDServiceTest<Payment, Long> {

    @InjectMocks
    private PaymentServiceImpl service;

    @Mock
    private PaymentRepository repository;

    @Mock
    private SequenceRepository sequenceRepository;

    @Override
    protected MongoRepository<Payment, Long> getRepository() {
        return repository;
    }

    @Override
    protected AbstractServiceDTO<Payment, Long> getService() {
        return service;
    }

    @Override
    public Long getCorrectID() {
        return 5L;
    }

    @Override
    public Long getIncorrectCorrectID() {
        return 42L;
    }

    @Override
    public Payment buildExpectedElement(Long id, Object... params) {
        Payment element = new Payment();
        element.setId(id);
        return element;
    }

    @Override
    public Payment buildCompleteElement(Long id, Object... params) {
        Payment element = new Payment();
        element.setId(id);
        return element;
    }

    @Override
    public Payment builPartialElement(Object... params) {
        return new Payment();
    }

    @Override
    public void initFindCorrect(Long id) {
        Payment element = new Payment();
        element.setId(id);
        initFindCorrect(element, id);
    }

    @Override
    public Class<Payment> getElementClass() {
        return Payment.class;
    }

    @Override
    public void mockIdGeneration() {
    }

    @Override
    protected void whenCreation(Boolean useId) {
        Long id = getCorrectID();
        Payment expected = buildExpectedElement(id);
        Mockito.when(getRepository().save(Mockito.any(getElementClass()))).thenReturn(expected);
        Mockito.when(getRepository().findById(id)).thenReturn(Optional.of(expected));
        Payment actual = useId ? getService().create(id) : getService().create();
        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(getElementClass());
        Mockito.verify(getRepository()).save(captor.capture());
        assertNotNull(captor.getValue());
        assertEquals(expected, captor.getValue());
        assertEquals(expected, actual);
    }

    @Override
    @Test
    public void givenElementThenSaveIt() {
        Long id = getCorrectID();
        Payment expected = buildExpectedElement(id);
        Mockito.when(getRepository().findById(id)).thenReturn(Optional.of(expected));
        Mockito.when(getRepository().save(expected)).thenReturn(expected);
        Mockito.when(validator.validate(expected)).thenReturn(null);
        Payment actual = getService().save(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void givenIdThenAcceptPayment() {
        Long id = getCorrectID();
        Payment previous = buildExpectedElement(id);
        Payment expected = buildExpectedElement(id);
        expected.setPaid(true);
        Mockito.when(getRepository().findById(id)).thenReturn(Optional.of(previous));
        Mockito.when(getRepository().save(expected)).thenReturn(expected);
        Payment actual = service.accept(id);
        assertEquals(expected, actual);
    }

    @Override
    @Test
    public void givenIdThenCreateElement() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> whenCreation(true));
        assertEquals(AbstractServiceDTO.AUTOGEN_ID, exception.getMessage());
    }

    @Override
    @Test
    public void givenNoneThenCreateIt() {
        Mockito.when(sequenceRepository.getNextSequenceId(Payment.SEQUENCE)).thenReturn(getCorrectID());
        whenCreation(false);
    }

}
