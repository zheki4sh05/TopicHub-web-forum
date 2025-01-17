package com.example.topichubbackend.util;
import com.example.topichubbackend.exceptions.*;
import jakarta.validation.*;


import java.util.*;

public class CustomValidator {

   private final ValidatorFactory factory;
   private final Validator validator;

    {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private final List<String> messages = new ArrayList<>();

    public <T> void validate(T obj){
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        for (ConstraintViolation<T> violation : violations) {
            messages.add(violation.getMessage());
        }
        checkList(messages);
    }

    private void checkList(List<String> messages){
        if(!messages.isEmpty()){
           throw new BadRequestException(messages);
        }
    }




}
