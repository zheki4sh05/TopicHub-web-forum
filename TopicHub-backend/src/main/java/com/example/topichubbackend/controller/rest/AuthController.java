package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final IAuthService authService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> register(
          @RequestBody @Valid  UserDto authDto
    ){
        log.info("register user dto: {}", authDto);
        authService.register(authDto);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> login(
            @Valid  @RequestBody  AuthDto authDto){

//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authDto.getData(),
//                        authDto.getPassword()));

//        UserDto userDto = authService.login(authDto);
        var userDto=UserDto.builder().build();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
