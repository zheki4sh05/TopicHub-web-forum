package com.example.topichubbackend.exceptions;

public class UserBlockException extends ImplRuntimeException{
    public UserBlockException() {
        super(ErrorKey.USER_BLOCKED.type());
    }

    public UserBlockException(String message) {
        super(message);
    }
}
