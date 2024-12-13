package com.example.topichubbackend.exceptions;

public class UserBlockException extends RuntimeException{
    public UserBlockException() {
    }

    public UserBlockException(String message) {
        super(message);
    }
}
