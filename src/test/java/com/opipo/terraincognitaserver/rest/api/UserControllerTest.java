package com.opipo.terraincognitaserver.rest.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;
import com.opipo.terraincognitaserver.service.UserService;

public class UserControllerTest extends AbstractCRUDControllerTest<User, String> {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private static final String PASSWORD = "4815162342";

    private static final String PASSWORD_ENCODED = "LOST";

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
        user.setPassword(PASSWORD);
        Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD_ENCODED);
        return user;
    }

}
