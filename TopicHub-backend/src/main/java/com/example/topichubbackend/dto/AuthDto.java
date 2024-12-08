package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDto {
    private String data;

    @NotBlank
    @Size(min = 6, max = 12)
    private String password;
}
