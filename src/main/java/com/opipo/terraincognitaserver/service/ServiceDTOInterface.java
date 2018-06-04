package com.opipo.terraincognitaserver.service;

import java.io.Serializable;
import java.util.Collection;

public interface ServiceDTOInterface<T, ID extends Serializable> {
    T find(ID id);

    Collection<T> findAll();

    T create();

    T create(ID id);

    T save(T element);

    T update(ID id, T element);

    void delete(T element);

    void delete(ID id);

    Boolean exists(ID id);

    void checkExists(ID id) throws IllegalArgumentException;

    void checkDoesntExists(ID id) throws IllegalArgumentException;
}