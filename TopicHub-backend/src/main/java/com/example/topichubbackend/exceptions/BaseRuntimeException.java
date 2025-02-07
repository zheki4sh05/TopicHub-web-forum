package com.example.topichubbackend.exceptions;

import com.example.topichubbackend.config.i18n.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public abstract class BaseRuntimeException extends RuntimeException{

    @Autowired
    private I18nUtil i18nUtil;

    protected I18nUtil getResourceBundle(){
        return i18nUtil;
    }
    @Override
    public abstract String getLocalizedMessage();

    public abstract Integer getCode();

}
