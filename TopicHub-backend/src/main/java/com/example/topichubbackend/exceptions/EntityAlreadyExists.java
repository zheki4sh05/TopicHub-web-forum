package com.example.topichubbackend.exceptions;


public class EntityAlreadyExists extends RuntimeException
{
    public EntityAlreadyExists() {
        super(ErrorKey.CONFLICT.key());
    }
    public EntityAlreadyExists(String message) {
        super(message);
    }

}
