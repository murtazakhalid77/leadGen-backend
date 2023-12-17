package com.leadgen.backend.exception;

public class UserExistException  extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
    // Additional constructors or methods if needed
}

