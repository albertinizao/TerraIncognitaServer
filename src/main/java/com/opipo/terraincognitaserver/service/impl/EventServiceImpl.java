package com.opipo.terraincognitaserver.service.impl;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.CharacterGroup;
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
    public Event save(Event element) {
        element.setCharacterGroups(getRepository().findById(element.getName()).get().getCharacterGroups());
        validate(element);
        return getRepository().save(element);
    }

    @Override
    public Event update(String id, Event element) {
        Event old = getRepository().findById(id).get();
        Collection<CharacterGroup> characterGroups = old.getCharacterGroups();
        BeanUtils.copyProperties(element, old);
        old.setCharacterGroups(characterGroups);
        validate(old);
        return getRepository().save(old);
    }
}
