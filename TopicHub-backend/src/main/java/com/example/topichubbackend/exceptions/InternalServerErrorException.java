package com.example.topichubbackend.exceptions;

public class InternalServerErrorException extends ImplRuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
    public InternalServerErrorException() {
        super(ErrorKey.SERVER_ERROR.type());
    }
}
