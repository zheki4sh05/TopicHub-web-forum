package com.example.topichubbackend.util;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import lombok.extern.slf4j.*;

import java.time.*;

@Slf4j
public class HttpResponseHandler {
    public static String error(ImplRuntimeException e){
        return  JsonMapper.mapTo(ErrorDto.builder()
                .code(e.getCode())
                .localDate(LocalDateTime.now().toString())
                .message(e.getLocalizedMessage())
                .build());
    }
    public static String error(BadRequestException e){
        return JsonMapper.mapTo(ErrorDto.builder()
                .code(e.getCode())
                .localDate(LocalDateTime.now().toString())
                .message(e.getLocalizedMessages())
                .build());
    }
}
