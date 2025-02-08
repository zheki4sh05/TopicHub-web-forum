package com.example.topichubbackend.security.dto;

import com.example.topichubbackend.dto.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("message")
    private String message;

    private UserDto userDto;
}
