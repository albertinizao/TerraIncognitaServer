package com.opipo.terraincognitaserver.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.repository.UserRepository;
import com.opipo.terraincognitaserver.service.UserService;

@Service
public class UserServiceImpl extends AbstractServiceDTO<User, String> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
    public User update(String id, User element) {
        User old = getRepository().findById(id).get();
        String pass = old.getPassword();
        if (!passwordEncoder.matches(element.getPassword(), pass)) {
            throw new UnauthorizedUserException(WRONG_PASSWORD);
        }
        BeanUtils.copyProperties(element, old);
        old.setPassword(pass);
        validate(old);
        return getRepository().save(old);
    }

}
