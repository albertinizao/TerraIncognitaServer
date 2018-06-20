package com.opipo.terraincognitaserver.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Location;
import com.opipo.terraincognitaserver.repository.LocationRepository;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;
import com.opipo.terraincognitaserver.service.impl.LocationServiceImpl;

public class LocationServiceTest extends GenericCRUDServiceTest<Location, String> {

    @InjectMocks
    private LocationServiceImpl service;

    @Mock
    private LocationRepository repository;

    @Override
    protected MongoRepository<Location, String> getRepository() {
        return repository;
    }

    @Override
    protected AbstractServiceDTO<Location, String> getService() {
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
    public Location buildExpectedElement(String id, Object... params) {
        Location element = new Location();
        element.setName(id);
        return element;
    }

    @Override
    public Location buildCompleteElement(String id, Object... params) {
        Location element = new Location();
        element.setName(id);
        return element;
    }

    @Override
    public Location builPartialElement(Object... params) {
        return new Location();
    }

    @Override
    public void initFindCorrect(String id) {
        Location element = new Location();
        element.setName(id);
        initFindCorrect(element, id);
    }

    @Override
    public Class<Location> getElementClass() {
        return Location.class;
    }

    @Override
    public void mockIdGeneration() {
    }

}
