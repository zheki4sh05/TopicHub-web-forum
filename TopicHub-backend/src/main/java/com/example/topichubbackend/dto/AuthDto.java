package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDto {
    private String data;

    @NotBlank(message = "PASS_EMPTY")
    @NotEmpty(message = "PASS_EMPTY")
    @Size(min = 6, max = 12,message = "PASS_INCORRECT")
    private String password;
}
