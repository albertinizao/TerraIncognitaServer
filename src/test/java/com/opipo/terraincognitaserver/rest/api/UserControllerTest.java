package com.opipo.terraincognitaserver.rest.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;
import com.opipo.terraincognitaserver.service.UserService;

public class UserControllerTest extends AbstractCRUDControllerTest<User, String> {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Override
    AbstractCRUDController<User, String> getController() {
        return userController;
    }

    @Override
    ServiceDTOInterface<User, String> getService() {
        return userService;
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
    User buildElement(String id) {
        User user = new User();
        user.setUsername(id);
        return user;
    }

}
