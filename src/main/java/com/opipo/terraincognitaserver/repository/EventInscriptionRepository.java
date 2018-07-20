package com.opipo.terraincognitaserver.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.EventInscriptionId;

public interface EventInscriptionRepository extends MongoRepository<EventInscription, EventInscriptionId> {

    @Query("{'id.event':?0}")
    Collection<EventInscription> findByEvent(String event);
}
