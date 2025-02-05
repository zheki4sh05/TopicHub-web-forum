package com.example.topichubbackend.exceptions;

public class EntityNotFoundException extends ImplRuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super(ErrorKey.NOT_FOUND.type());
    }
}
