package com.example.topichubbackend.aspect;

import com.example.topichubbackend.config.i18n.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

import java.time.*;
import java.util.*;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionControllerAdvice {
    private final I18nUtil i18nUtil;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> exceptionEntityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(EntityAlreadyExists.class)
//    public ResponseEntity<?> exceptionSuchEntityAlreadyExists(EntityAlreadyExists e) {
//        var code = HttpStatus.CONFLICT;
//        ErrorDto errorDto = ErrorDto.builder()
//                .code(code.value())
//                .message(e.getMessage())
//                .localDate(LocalDate.now().toString())
//                .build();
//        return new ResponseEntity<>(errorDto, code);
//    }

    @ExceptionHandler(EntityAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?>  handleIllegalArgumentException(EntityAlreadyExists e, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = i18nUtil.getMessage(ErrorKey.CONFLICT.type(),locale, null);
        var code = HttpStatus.CONFLICT;
        ErrorDto errorDto = ErrorDto.builder()
                .code(code.value())
                .message(errorMessage)
                .localDate(LocalDate.now().toString())
                .build();
        return new ResponseEntity<>(errorDto, code);
    }


//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> exceptionBadRequest(RuntimeException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }

}
