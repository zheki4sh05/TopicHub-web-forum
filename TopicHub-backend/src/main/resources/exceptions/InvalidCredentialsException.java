package com.example.topichubbackend.exceptions;

public class InvalidCredentialsException extends ImplRuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
