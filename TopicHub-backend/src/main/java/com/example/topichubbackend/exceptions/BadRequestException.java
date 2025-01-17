package com.example.topichubbackend.exceptions;
import lombok.extern.slf4j.*;

import java.util.*;
import java.util.stream.*;

@Slf4j
public class BadRequestException extends ImplRuntimeException{
    private List<String> messages = new ArrayList<>(1);

    public BadRequestException(List<String> messages) {
        super(null);
        this.messages = messages;
    }
    public BadRequestException(String message) {
        super(message);
        messages.add(message);
    }

    public BadRequestException() {
        super(ErrorKey.WRONG_REQUEST_PARAM.type());
    }
    public String getLocalizedMessages() {
        String str = messages.stream().map(item->
                BaseRuntimeException.getResourceBundle().getString(ErrorKey.valueOf(item).type())
        ).collect(Collectors.joining(" "));
        messages.clear();
        return str;
    }
    @Override
    public Integer getCode() {
        return 400;
    }

}
