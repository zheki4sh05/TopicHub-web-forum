package com.example.topichubbackend.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super("Article not found");
    }
}
