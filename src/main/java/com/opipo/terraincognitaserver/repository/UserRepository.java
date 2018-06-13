package com.opipo.terraincognitaserver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.User;

public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Retrieves an entity by its id.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}.
     */
    Optional<User> findByName(String name);
}
