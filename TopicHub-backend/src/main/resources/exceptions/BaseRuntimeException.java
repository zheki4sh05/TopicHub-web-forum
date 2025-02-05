package com.example.topichubbackend.exceptions;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

public abstract class BaseRuntimeException extends RuntimeException{

//    @Autowired
//    private static final ResourceBundle RESOURCE_BUNDLE;

//    protected static ResourceBundle getResourceBundle(){
//        return RESOURCE_BUNDLE;
//    }
    @Override
    public abstract String getLocalizedMessage();

    public abstract Integer getCode();

}
