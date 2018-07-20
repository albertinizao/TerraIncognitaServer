package com.opipo.terraincognitaserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.EventInscriptionId;
import com.opipo.terraincognitaserver.repository.EventInscriptionRepository;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;
import com.opipo.terraincognitaserver.service.impl.EventInscriptionServiceImpl;

public class EventInscriptionServiceTest extends GenericCRUDServiceTest<EventInscription, EventInscriptionId> {

    @InjectMocks
    private EventInscriptionServiceImpl service;

    @Mock
    private EventInscriptionRepository repository;

    @Mock
    private EventPaymentService eventPaymentService;

    @Override
    protected MongoRepository<EventInscription, EventInscriptionId> getRepository() {
        return repository;
    }

    @Override
    protected AbstractServiceDTO<EventInscription, EventInscriptionId> getService() {
        return service;
    }

    @Override
    public EventInscriptionId getCorrectID() {
        EventInscriptionId id = new EventInscriptionId();
        id.setEvent("eventId");
        id.setUsername("usernameId");
        return id;
    }

    @Override
    public EventInscriptionId getIncorrectCorrectID() {
        EventInscriptionId id = new EventInscriptionId();
        id.setEvent("fake");
        id.setUsername("fake");
        return id;
    }

    @Override
    public EventInscription buildExpectedElement(EventInscriptionId id, Object... params) {
        EventInscription element = new EventInscription();
        element.setId(id);
        return element;
    }

    @Override
    public EventInscription buildCompleteElement(EventInscriptionId id, Object... params) {
        EventInscription element = new EventInscription();
        element.setId(id);
        return element;
    }

    @Override
    public EventInscription builPartialElement(Object... params) {
        return new EventInscription();
    }

    @Override
    public void initFindCorrect(EventInscriptionId id) {
        EventInscription element = new EventInscription();
        element.setId(id);
        initFindCorrect(element, id);
    }

    @Override
    public Class<EventInscription> getElementClass() {
        return EventInscription.class;
    }

    @Override
    public void mockIdGeneration() {
    }

    @Override
    protected void whenCreation(Boolean useId) {
        EventInscriptionId id = getCorrectID();
        EventInscription expected = buildExpectedElement(id);
        Mockito.when(getRepository().save(Mockito.any(getElementClass()))).thenReturn(expected);
        Mockito.when(getRepository().findById(id)).thenReturn(Optional.of(expected));
        EventInscription actual = useId ? getService().create(id) : getService().create();
        ArgumentCaptor<EventInscription> captor = ArgumentCaptor.forClass(getElementClass());
        Mockito.verify(getRepository()).save(captor.capture());
        assertNotNull(captor.getValue());
        assertEquals(expected, captor.getValue());
        assertEquals(expected, actual);
    }

    @Override
    @Test
    public void givenElementThenSaveIt() {
        EventInscriptionId id = getCorrectID();
        EventInscription expected = buildExpectedElement(id);
        Mockito.when(getRepository().findById(id)).thenReturn(Optional.of(expected));
        Mockito.when(getRepository().save(expected)).thenReturn(expected);
        Mockito.when(validator.validate(expected)).thenReturn(null);
        EventInscription actual = getService().save(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void assignCharacter() {
        String characterName = "Menganito";
        EventInscriptionId id = getCorrectID();
        EventInscription previous = buildExpectedElement(id);
        EventInscription expected = buildExpectedElement(id);
        expected.setAssignedCharacter(characterName);

        Mockito.when(getRepository().findById(id)).thenReturn(Optional.of(previous));
        Mockito.when(getRepository().save(expected)).thenReturn(expected);
        Mockito.when(validator.validate(expected)).thenReturn(null);
        EventInscription actual = service.assignCharacter(id, characterName);
        assertEquals(expected, actual);
    }

    @Test
    public void givenEventIdThenReturnEventInscriptions() {
        String eventId = "eventId";
        EventInscription ei = new EventInscription();
        Collection<EventInscription> expected = Arrays.asList(ei);
        Mockito.when(repository.findByEvent(eventId)).thenReturn(expected);

        Collection<EventInscription> actual = service.findByEventId(eventId);

        assertEquals(expected, actual);
    }

    @Override
    @Test
    public void givenIdThenCreateElement() {
        super.givenIdThenCreateElement();
        Mockito.verify(eventPaymentService).createPayment(Mockito.any());
    }

}
