package com.advertisementboard.exception.entity;

public class EntityNotExistException extends EntityException {

    public static final String DEFAULT_MESSAGE = "The entity with %s does not exist";

    public EntityNotExistException(Long id) {
        super(String.format(DEFAULT_MESSAGE, "id=" + id));
    }

}
