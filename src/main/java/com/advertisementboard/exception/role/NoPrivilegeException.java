package com.advertisementboard.exception.role;

public class NoPrivilegeException extends RuntimeException{

    public static final String DEFAULT_MESSAGE = "Not enough privileges for this action";

    public NoPrivilegeException(){
        super(DEFAULT_MESSAGE);
    }

}
