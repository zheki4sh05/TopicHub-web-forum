package com.example.topichubbackend.dto;

import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String id;
    private String login;
    private String email;
    private String password;
    private List<String> roles;
}
