package com.example.topichubbackend.config.i18n;

import lombok.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@AllArgsConstructor
public class I18nUtil {

    private final MessageSource messageSource;
    public String getMessage(String code,Locale locale, String... args){
        return messageSource.getMessage(code, args, locale);
    }

}