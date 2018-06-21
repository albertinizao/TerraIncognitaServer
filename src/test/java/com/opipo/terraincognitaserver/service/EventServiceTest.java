package com.opipo.terraincognitaserver.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.repository.EventRepository;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;
import com.opipo.terraincognitaserver.service.impl.EventServiceImpl;

public class EventServiceTest extends GenericCRUDServiceTest<Event, String> {

    @InjectMocks
    private EventServiceImpl service;

    @Mock
    private EventRepository repository;

    @Override
    protected MongoRepository<Event, String> getRepository() {
        return repository;
    }

    @Override
    protected AbstractServiceDTO<Event, String> getService() {
        return service;
    }

    @Override
    public String getCorrectID() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getIncorrectCorrectID() {
        return "42";
    }

    @Override
    public Event buildExpectedElement(String id, Object... params) {
        Event element = new Event();
        element.setName(id);
        return element;
    }

    @Override
    public Event buildCompleteElement(String id, Object... params) {
        Event element = new Event();
        element.setName(id);
        return element;
    }

    @Override
    public Event builPartialElement(Object... params) {
        return new Event();
    }

    @Override
    public void initFindCorrect(String id) {
        Event element = new Event();
        element.setName(id);
        initFindCorrect(element, id);
    }

    @Override
    public Class<Event> getElementClass() {
        return Event.class;
    }

    @Override
    public void mockIdGeneration() {
    }

}
