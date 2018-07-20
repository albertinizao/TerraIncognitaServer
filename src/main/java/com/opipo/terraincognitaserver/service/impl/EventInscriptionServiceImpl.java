package com.opipo.terraincognitaserver.service.impl;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.EventInscriptionId;
import com.opipo.terraincognitaserver.repository.EventInscriptionRepository;
import com.opipo.terraincognitaserver.service.EventInscriptionService;
import com.opipo.terraincognitaserver.service.EventPaymentService;

@Service
public class EventInscriptionServiceImpl extends AbstractServiceDTO<EventInscription, EventInscriptionId>
        implements EventInscriptionService {

    @Autowired
    private EventInscriptionRepository repository;

    @Autowired
    private EventPaymentService eventPaymentService;

    @Override
    protected MongoRepository<EventInscription, EventInscriptionId> getRepository() {
        return repository;
    }

    @Override
    protected EventInscription buildElement(EventInscriptionId id) {
        EventInscription element = new EventInscription();
        element.setId(id);
        return element;
    }

    @Override
    public EventInscriptionId buildId() {
        throw new UnsupportedOperationException(AbstractServiceDTO.NEEDS_ID);
    }

    @Override
    protected Consumer<EventInscription> preserveOldValues(EventInscription newValue) {
        return c -> {

        };
    }

    @Override
    protected Function<EventInscription, EventInscriptionId> getId() {
        return f -> f.getId();
    }

    @Override
    public EventInscription saveComplete(EventInscription event) {
        validate(event);
        return getRepository().save(event);
    }

    @Override
    public EventInscription assignCharacter(EventInscriptionId id, String characterName) {
        EventInscription eventInscription = find(id);
        eventInscription.setAssignedCharacter(characterName);
        return saveComplete(eventInscription);
    }

    @Override
    public Collection<EventInscription> findByEventId(String eventId) {
        return repository.findByEvent(eventId);
    }

    @Override
    public EventInscription create(EventInscriptionId id) {
        EventInscription response = super.create(id);
        eventPaymentService.createPayment(response);
        return response;
    }
}
