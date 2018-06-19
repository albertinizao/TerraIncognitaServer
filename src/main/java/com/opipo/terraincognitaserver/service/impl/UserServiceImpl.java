package com.opipo.terraincognitaserver.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.repository.UserRepository;
import com.opipo.terraincognitaserver.service.RoleService;
import com.opipo.terraincognitaserver.service.UserService;

@Service
public class UserServiceImpl extends AbstractServiceDTO<User, String> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    protected MongoRepository<User, String> getRepository() {
        return userRepository;
    }

    @Override
    protected User buildElement(String id) {
        User user = new User();
        user.setUsername(id);
        return user;
    }

    @Override
    public String buildId() {
        throw new UnsupportedOperationException(AbstractServiceDTO.NEEDS_ID);
    }

    @Override
    public User save(User element) {
        Optional<User> previous = getRepository().findById(element.getUsername());
        if (previous.isPresent()) {// Si es actualización
            element.setPassword(previous.get().getPassword());
            element.setRoles(previous.get().getRoles());
        }
        validate(element);
        return getRepository().save(element);
    }

    @Override
    public User update(String id, User element) {
        User old = getRepository().findById(id).get();
        String pass = old.getPassword();
        List<Role> roles = old.getRoles();
        BeanUtils.copyProperties(element, old);
        old.setPassword(pass);
        old.setRoles(roles);
        validate(old);
        return getRepository().save(old);
    }

    @Override
    public User changePassword(User user) {
        return getRepository().save(user);
    }

    @Override
    public User addRole(String id, String roleId) {
        roleService.exists(roleId);
        Role role = roleService.find(roleId);
        User user = getRepository().findById(id).get();
        user.addRole(role);
        return userRepository.save(user);
    }

}
