package com.example.topichubbackend.dto;
import lombok.*;

import java.time.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorDto {
    private Integer code;
    private String message;
}

//public record ErrorDto(Integer code, String message, LocalDateTime localDateTime) {
//
//}
