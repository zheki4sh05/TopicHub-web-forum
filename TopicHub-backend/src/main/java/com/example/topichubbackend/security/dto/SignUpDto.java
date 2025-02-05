package com.example.topichubbackend.security.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String login;
    private String email;
    private String password;
}
