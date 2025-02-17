package com.example.topichubbackend.exceptions;

public class MinioStorageException extends RuntimeException{
    public MinioStorageException(String message) {
        super(message);
    }

}
