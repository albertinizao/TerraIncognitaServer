package com.opipo.terraincognitaserver.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.repository.RoleRepository;
import com.opipo.terraincognitaserver.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractServiceDTO<Role, String> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected MongoRepository<Role, String> getRepository() {
        return roleRepository;
    }

    @Override
    protected Role buildElement(String id) {
        Role role = new Role();
        role.setName(id);
        return role;
    }

    @Override
    public String buildId() {
        throw new UnsupportedOperationException(AbstractServiceDTO.NEEDS_ID);
    }

    @Override
    public boolean canManageRole(Authentication authentication, String role) {
        List<String> roles = Arrays.asList(new String[] {role + "MANAGE", "ADMIN", "JUNTA"});
        return authentication.getAuthorities().stream()
                .anyMatch(p -> roles.stream().anyMatch(p2 -> p.getAuthority().equalsIgnoreCase(p2)));
    }

}
