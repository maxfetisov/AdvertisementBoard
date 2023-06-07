package com.advertisementboard.exception.athentication;

public class NoAuthenticationException extends RuntimeException{

    public static final String DEFAULT_MESSAGE = "No authentication";

    public NoAuthenticationException() {
        super(DEFAULT_MESSAGE);
    }

}
