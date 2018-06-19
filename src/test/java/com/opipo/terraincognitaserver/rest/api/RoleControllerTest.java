package com.opipo.terraincognitaserver.rest.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.service.RoleService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

public class RoleControllerTest extends AbstractCRUDControllerTest<Role, String> {

    @InjectMocks
    private RoleController controller;

    @Mock
    private RoleService service;

    @Override
    AbstractCRUDController<Role, String> getController() {
        return controller;
    }

    @Override
    ServiceDTOInterface<Role, String> getService() {
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
    Role buildElement(String id) {
        Role role = new Role();
        role.setName(id);
        return role;
    }

}
