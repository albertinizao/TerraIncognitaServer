package com.opipo.terraincognitaserver.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;
import com.opipo.terraincognitaserver.service.UserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/user")
@Api(value = "REST API to manage users")
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
