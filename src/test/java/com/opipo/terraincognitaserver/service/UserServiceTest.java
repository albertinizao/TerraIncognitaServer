package com.opipo.terraincognitaserver.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.repository.UserRepository;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;
import com.opipo.terraincognitaserver.service.impl.UserServiceImpl;

public class UserServiceTest extends GenericCRUDServiceTest<User, String> {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Override
    protected MongoRepository<User, String> getRepository() {
        return repository;
    }

    @Override
    protected AbstractServiceDTO<User, String> getService() {
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
    public User buildExpectedElement(String id, Object... params) {
        User user = new User();
        user.setUsername(id);
        return user;
    }

    @Override
    public User buildCompleteElement(String id, Object... params) {
        User user = new User();
        user.setUsername(id);
        return user;
    }

    @Override
    public User builPartialElement(Object... params) {
        return new User();
    }

    @Override
    public void initFindCorrect(String id) {
        User user = new User();
        user.setUsername(id);
        initFindCorrect(user, id);
    }

    @Override
    public Class<User> getElementClass() {
        return User.class;
    }

    @Override
    public void mockIdGeneration() {
    }

}
