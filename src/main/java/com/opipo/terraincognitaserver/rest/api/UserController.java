package com.opipo.terraincognitaserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;
import com.opipo.terraincognitaserver.service.UserService;

public class UserController extends AbstractCRUDController<User, String> {

    @Autowired
    private UserService service;

    @Override
    protected ServiceDTOInterface<User, String> getService() {
        return service;
    }

    @Override
    protected String getIdFromElement(User element) {
        return element.getUsername();
    }

}
