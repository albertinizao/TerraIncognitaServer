package com.opipo.terraincognitaserver.service.impl;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.repository.EventRepository;
import com.opipo.terraincognitaserver.service.EventService;

@Service
public class EventServiceImpl extends AbstractServiceDTO<Event, String> implements EventService {

    @Autowired
    private EventRepository repository;

    @Override
    protected MongoRepository<Event, String> getRepository() {
        return repository;
    }

    @Override
    protected Event buildElement(String id) {
        Event element = new Event();
        element.setName(id);
        return element;
    }

    @Override
    public String buildId() {
        throw new UnsupportedOperationException(AbstractServiceDTO.NEEDS_ID);
    }

    @Override
    protected Consumer<Event> preserveOldValues(Event newValue) {
        return c -> {
            newValue.setCharacterGroups(c.getCharacterGroups());
        };
    }

    @Override
    protected Function<Event, String> getId() {
        return f -> f.getName();
    }

    @Override
    public Event saveComplete(Event event) {
        validate(event);
        return getRepository().save(event);
    }
}
