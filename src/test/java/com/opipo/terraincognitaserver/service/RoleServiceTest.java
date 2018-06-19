package com.opipo.terraincognitaserver.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.repository.RoleRepository;
import com.opipo.terraincognitaserver.service.impl.AbstractServiceDTO;
import com.opipo.terraincognitaserver.service.impl.RoleServiceImpl;

public class RoleServiceTest extends GenericCRUDServiceTest<Role, String> {

    @InjectMocks
    private RoleServiceImpl service;

    @Mock
    private RoleRepository repository;

    @Override
    protected MongoRepository<Role, String> getRepository() {
        return repository;
    }

    @Override
    protected AbstractServiceDTO<Role, String> getService() {
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
    public Role buildExpectedElement(String id, Object... params) {
        Role role = new Role();
        role.setName(id);
        return role;
    }

    @Override
    public Role buildCompleteElement(String id, Object... params) {
        Role role = new Role();
        role.setName(id);
        return role;
    }

    @Override
    public Role builPartialElement(Object... params) {
        return new Role();
    }

    @Override
    public void initFindCorrect(String id) {
        Role role = new Role();
        role.setName(id);
        initFindCorrect(role, id);
    }

    @Override
    public Class<Role> getElementClass() {
        return Role.class;
    }

    @Override
    public void mockIdGeneration() {
    }

}
