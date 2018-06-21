package com.opipo.terraincognitaserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.dto.Event;

public interface EventRepository extends MongoRepository<Event, String> {

}
