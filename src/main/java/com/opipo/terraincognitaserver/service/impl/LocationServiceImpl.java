package com.opipo.terraincognitaserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.Location;
import com.opipo.terraincognitaserver.repository.LocationRepository;
import com.opipo.terraincognitaserver.service.LocationService;

@Service
public class LocationServiceImpl extends AbstractServiceDTO<Location, String> implements LocationService {

    @Autowired
    private LocationRepository repository;

    @Override
    protected MongoRepository<Location, String> getRepository() {
        return repository;
    }

    @Override
    protected Location buildElement(String id) {
        Location element = new Location();
        element.setName(id);
        return element;
    }

    @Override
    public String buildId() {
        throw new UnsupportedOperationException(AbstractServiceDTO.NEEDS_ID);
    }
}
