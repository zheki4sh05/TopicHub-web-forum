package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private String id;

    @NotBlank(message = "User login must not be empty")
    private String login;

    @Email(message = "Email must be correct")
    private String email;

    @NotBlank
    @Size(min = 6, max = 12)
    private String password;

    private List<String> roles;
}
