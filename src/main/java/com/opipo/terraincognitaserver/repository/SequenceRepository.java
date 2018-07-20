package com.opipo.terraincognitaserver.repository;

public interface SequenceRepository {

    long getNextSequenceId(String key);
}
