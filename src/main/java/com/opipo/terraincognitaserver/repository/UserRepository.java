package com.opipo.terraincognitaserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.User;

public interface UserRepository extends MongoRepository<User, String> {

}
