package com.example.topichubbackend.exceptions;

public class SuchLoginAlreadyExistsException extends RuntimeException{
    public SuchLoginAlreadyExistsException() {
        super("Пользователь с таким логином уже существует!");
    }
}
