package com.example.topichubbackend.exceptions;

import java.util.*;
import java.util.stream.*;

public class BadRequestException extends RuntimeException{
    public BadRequestException(List<String> messages) {
        super(messages.stream().collect(Collectors.joining("; ")));
    }

    public BadRequestException() {
    }
}
