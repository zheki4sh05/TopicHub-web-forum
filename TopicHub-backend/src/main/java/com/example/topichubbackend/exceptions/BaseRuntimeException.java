package com.example.topichubbackend.exceptions;

import java.util.*;

public abstract class BaseRuntimeException extends RuntimeException{
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages", Locale.getDefault());

    protected static ResourceBundle getResourceBundle(){
        return RESOURCE_BUNDLE;
    }
    @Override
    public abstract String getLocalizedMessage();

    public abstract Integer getCode();

}
