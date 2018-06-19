package com.opipo.terraincognitaserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

}
