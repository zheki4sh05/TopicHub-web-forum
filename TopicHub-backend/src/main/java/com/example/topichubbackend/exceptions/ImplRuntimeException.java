package com.example.topichubbackend.exceptions;

public class ImplRuntimeException extends BaseRuntimeException{

    private final String messageKey;
    public ImplRuntimeException(String message) {
        messageKey = message;
    }

    @Override
    public String getLocalizedMessage() {
        if(messageKey!=null){
            return "";
        }else{
            return "";
        }

    }

    @Override
    public Integer getCode() {
        return 500;
    }
}
