package com.example.topichubbackend.exceptions;


public class EntityAlreadyExists extends ImplRuntimeException{
    public EntityAlreadyExists() {
        super(ErrorKey.CONFLICT.type());
    }
    public EntityAlreadyExists(String message) {
        super(message);
    }

    @Override
    public Integer getCode() {
        return 409;
    }
}
