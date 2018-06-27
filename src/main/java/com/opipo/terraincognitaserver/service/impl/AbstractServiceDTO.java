package com.opipo.terraincognitaserver.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

public abstract class AbstractServiceDTO<T, ID extends Serializable> implements ServiceDTOInterface<T, ID> {

    public static String DOESNT_EXISTS = "ERR:RESOURCE:01";
    public static String EXISTS = "ERR:RESOURCE:02";
    public static String NEEDS_ID = "ERR:RESOURCE:03";
    public static String WRONG_PASSWORD = "ERR:PASSWORD:01";

    @Autowired
    protected Validator validator;

    protected abstract MongoRepository<T, ID> getRepository();

    protected abstract T buildElement(ID id);

    public abstract ID buildId();

    @Override
    public Boolean exists(ID id) {
        return this.find(id) != null;
    }

    @Override
    public void checkExists(ID id) throws IllegalArgumentException {
        if (!exists(id)) {
            throw new IllegalArgumentException(DOESNT_EXISTS);
        }
    }

    @Override
    public void checkDoesntExists(ID id) throws IllegalArgumentException {
        if (exists(id)) {
            throw new IllegalArgumentException(EXISTS);
        }
    }

    @Override
    public T find(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public Collection<T> findAll() {
        return getRepository().findAll();
    }

    public T saveComplete(T element) {
        validate(element);
        return getRepository().save(element);
    }

    @Override
    public T save(T element) {
        getOptionalId(element).ifPresent(c -> getRepository().findById(c).ifPresent(preserveOldValues(element)));
        return saveComplete(element);
    }

    @Override
    public T update(ID id, T element) {
        T old = getRepository().findById(id).get();
        preserveOldValues(element).accept(old);
        BeanUtils.copyProperties(element, old);
        return saveComplete(old);
    }

    protected boolean validate(T element) {
        java.util.Set<javax.validation.ConstraintViolation<T>> validation = validator.validate(element);
        if (null != validation && !validation.isEmpty()) {
            throw new ConstraintViolationException(validation);
        } else {
            return true;
        }
    }

    @Override
    public void delete(T element) {
        getRepository().delete(element);
    }

    @Override
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public T create(ID id) {
        return this.save(buildElement(id));
    }

    @Override
    public T create() {
        return this.create(buildId());
    }

    protected Optional<ID> getOptionalId(T element) {
        return Optional.ofNullable(getId().apply(element));
    }

    protected Consumer<T> preserveOldValues(T newValue) {
        return c -> {
        };
    }

    protected Function<T, ID> getId() {
        return t -> null;
    }
}