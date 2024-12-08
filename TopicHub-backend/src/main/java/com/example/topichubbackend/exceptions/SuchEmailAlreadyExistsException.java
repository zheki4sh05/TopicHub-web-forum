package com.example.topichubbackend.exceptions;

public class SuchEmailAlreadyExistsException extends RuntimeException{
    public SuchEmailAlreadyExistsException() {
        super("Пользовательс таким email уже существует");
    }
}
