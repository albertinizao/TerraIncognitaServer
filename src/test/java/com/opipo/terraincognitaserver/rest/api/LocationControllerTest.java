package com.opipo.terraincognitaserver.rest.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.opipo.terraincognitaserver.dto.Location;
import com.opipo.terraincognitaserver.service.LocationService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

public class LocationControllerTest extends AbstractCRUDControllerTest<Location, String> {

    @InjectMocks
    private LocationController controller;

    @Mock
    private LocationService service;

    @Override
    protected AbstractCRUDController<Location, String> getController() {
        return controller;
    }

    @Override
    protected ServiceDTOInterface<Location, String> getService() {
        return service;
    }

    @Override
    protected String getCorrectID() {
        return "correctId";
    }

    @Override
    protected String getIncorrectID() {
        return "fakeId";
    }

    @Override
    protected Location buildElement(String id) {
        Location element = new Location();
        element.setName(id);
        return element;
    }

}
