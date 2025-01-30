package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.exceptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> exceptionEntityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExists.class)
    public ResponseEntity<?> exceptionSuchEntityAlreadyExists(EntityAlreadyExists e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> exceptionBadRequest(RuntimeException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }

}
