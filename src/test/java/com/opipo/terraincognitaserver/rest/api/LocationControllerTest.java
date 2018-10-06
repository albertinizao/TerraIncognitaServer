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
    AbstractCRUDController<Location, String> getController() {
        return controller;
    }

    @Override
    ServiceDTOInterface<Location, String> getService() {
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
    Location buildElement(String id) {
        Location element = new Location();
        element.setName(id);
        return element;
    }

}
