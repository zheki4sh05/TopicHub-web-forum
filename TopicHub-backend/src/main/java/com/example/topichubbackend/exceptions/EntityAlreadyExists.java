package com.example.topichubbackend.exceptions;


public class EntityAlreadyExists extends RuntimeException{
    public EntityAlreadyExists() {

    }

    public EntityAlreadyExists(String message) {
        super(message);
    }
}
