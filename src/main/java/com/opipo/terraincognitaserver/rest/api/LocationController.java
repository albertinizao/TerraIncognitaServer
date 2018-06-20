package com.opipo.terraincognitaserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.terraincognitaserver.dto.Location;
import com.opipo.terraincognitaserver.service.LocationService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/location")
@Api(value = "REST API to manage event locations")
public class LocationController extends AbstractCRUDController<Location, String> {

    @Autowired
    private LocationService service;

    @Override
    protected ServiceDTOInterface<Location, String> getService() {
        return service;
    }

    @Override
    protected String getIdFromElement(Location element) {
        return element.getName();
    }

}
