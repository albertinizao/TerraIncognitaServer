package com.opipo.terraincognitaserver.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.repository.UserRepository;
import com.opipo.terraincognitaserver.service.UserService;

@Service
public class UserServiceImpl extends AbstractServiceDTO<User, String> implements UserService {

    @Autowired
    private UserRepository userRepository;

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
        }
        validate(element);
        return getRepository().save(element);
    }

    @Override
    public User update(String id, User element) {
        User old = getRepository().findById(id).get();
        String pass = old.getPassword();
        BeanUtils.copyProperties(element, old);
        old.setPassword(pass);
        validate(old);
        return getRepository().save(old);
    }

    @Override
    public User changePassword(User user) {
        return getRepository().save(user);
    }

}
