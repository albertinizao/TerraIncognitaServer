package com.opipo.terraincognitaserver.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

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

    @Test
    @DisplayName("A user with role PRUEBAMANAGE can manage a role PRUEBA")
    public void givenAuthenticationAndRoleThatCanManageThenReturnTrue() {
        String roleString = "PRUEBA";
        Collection<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName(roleString + "MANAGE");
        roles.add(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "pass", roles);
        Boolean response = service.canManageRole(authentication, roleString);
        assertTrue("The user have to be able to manage role", response);
    }

    @Test
    @DisplayName("A user with role JUNTA can manage a role PRUEBA")
    public void givenAuthenticationAndRoleThatCanManageThenReturnTrue2() {
        String roleString = "PRUEBA";
        Collection<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("JUNTA");
        roles.add(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "pass", roles);
        Boolean response = service.canManageRole(authentication, roleString);
        assertTrue("The user have to be able to manage role", response);
    }

    @Test
    @DisplayName("A user with role ADMIN can manage a role PRUEBA")
    public void givenAuthenticationAndRoleThatCanManageThenReturnTrue3() {
        String roleString = "PRUEBA";
        Collection<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("ADMIN");
        roles.add(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "pass", roles);
        Boolean response = service.canManageRole(authentication, roleString);
        assertTrue("The user have to be able to manage role", response);
    }

    @Test
    @DisplayName("A user with role MASTER can't manage a role PRUEBA")
    public void givenAuthenticationAndRoleThatCanManageThenReturnFalse() {
        String roleString = "PRUEBA";
        Collection<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("MASTER");
        roles.add(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "pass", roles);
        Boolean response = service.canManageRole(authentication, roleString);
        assertFalse("The user mustn't be able to manage role", response);
    }

}
