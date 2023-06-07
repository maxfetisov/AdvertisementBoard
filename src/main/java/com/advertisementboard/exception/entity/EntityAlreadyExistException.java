package com.advertisementboard.exception.entity;

public class EntityAlreadyExistException extends EntityException {

    public static final String DEFAULT_MESSAGE = "The entity %s already exists";

    public EntityAlreadyExistException(String entity) {
        super(String.format(DEFAULT_MESSAGE, entity));
    }

}
