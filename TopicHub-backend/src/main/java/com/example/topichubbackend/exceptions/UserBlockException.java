package com.example.topichubbackend.exceptions;

public class UserBlockException extends RuntimeException{
    public UserBlockException() {
        super(ErrorKey.USER_BLOCKED.key());
    }

}
