package com.opipo.terraincognitaserver.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.repository.UserRepository;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;
import com.opipo.terraincognitaserver.service.impl.UserServiceImpl;

public class UserServiceTest extends GenericCRUDServiceTest<User, String> {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    private static final String PASSWORD = "4815162342";

    private static final String PASSWORD_ENCODED = "LOST";

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
        user.setPassword(PASSWORD);
        return user;
    }

    @Override
    public User buildCompleteElement(String id, Object... params) {
        User user = new User();
        user.setUsername(id);
        user.setPassword(PASSWORD_ENCODED);
        Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD_ENCODED);
        Mockito.when(passwordEncoder.matches(PASSWORD, PASSWORD_ENCODED)).thenReturn(true);
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

    @Test
    @DisplayName("Given user and role then add the role to the user")
    public void givenUserAndRoleThenAddRoleToUser() {
        String userId = "userId";
        String roleId = "roleId";
        Role role = new Role();
        role.setName(roleId);
        Mockito.when(roleService.find(roleId)).thenReturn(role);
        User user = new User();
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(repository.findById(userId)).thenReturn(userOptional);
        Mockito.when(repository.save(user)).thenReturn(user);

        User actual = service.addRole(userId, roleId);

        Mockito.verify(roleService).exists(roleId);

        assertTrue("The rule has to been added", actual.getRoles().contains(role));

    }

}
