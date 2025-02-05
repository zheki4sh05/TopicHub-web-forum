package com.example.topichubbackend.security.controller;

import com.example.topichubbackend.security.dto.*;
import com.example.topichubbackend.security.service.impl.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @RequestBody SignUpDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthDto request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/refresh")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }
}
