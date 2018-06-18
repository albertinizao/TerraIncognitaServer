package com.opipo.terraincognitaserver.service;

import com.opipo.terraincognitaserver.dto.User;

public interface UserService extends ServiceDTOInterface<User, String> {

    User changePassword(User user);

}
