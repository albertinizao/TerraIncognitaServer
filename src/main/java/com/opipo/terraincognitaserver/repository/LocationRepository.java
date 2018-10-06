package com.opipo.terraincognitaserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Location;

public interface LocationRepository extends MongoRepository<Location, String> {

}
