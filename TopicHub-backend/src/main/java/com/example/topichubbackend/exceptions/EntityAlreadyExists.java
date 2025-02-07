package com.example.topichubbackend.exceptions;


public class EntityAlreadyExists extends RuntimeException
{
    public EntityAlreadyExists() {
        super(ErrorKey.CONFLICT.type());
    }
    public EntityAlreadyExists(String message) {
        super(message);
    }

}
