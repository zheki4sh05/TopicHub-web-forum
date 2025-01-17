package com.example.topichubbackend.exceptions;

public class UserNotFoundException extends ImplRuntimeException{
    public UserNotFoundException() {

        super(ErrorKey.USER_NOT_FOUND.type());
    }

    @Override
    public Integer getCode() {
        return 404;
    }
}
