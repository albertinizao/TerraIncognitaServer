package com.opipo.terraincognitaserver.rest.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.service.EventService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

public class EventControllerTest extends AbstractCRUDControllerTest<Event, String> {

    @InjectMocks
    private EventController controller;

    @Mock
    private EventService service;

    @Override
    AbstractCRUDController<Event, String> getController() {
        return controller;
    }

    @Override
    ServiceDTOInterface<Event, String> getService() {
        return service;
    }

    @Override
    String getCorrectID() {
        return "correctId";
    }

    @Override
    String getIncorrectID() {
        return "fakeId";
    }

    @Override
    Event buildElement(String id) {
        Event element = new Event();
        element.setName(id);
        return element;
    }

}
