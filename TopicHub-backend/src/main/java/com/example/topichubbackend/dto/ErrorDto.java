package com.example.topichubbackend.dto;
import com.example.topichubbackend.exceptions.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorDto {

   private Integer code;
    private String message;
    private String localDate;
}

