package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling user authentication (signup and signin).
 * Provides endpoints for user registration (signup) and login (signin).
 *
 * <p>This controller is responsible for managing user authentication processes, including creating new users
 * and validating existing users for login. It integrates with the authentication service to perform user-related actions.
 *
 */
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final IAuthService authService;

    /**
     * Registers a new user in the system.
     *
     * <p>This endpoint accepts a request body containing user information (UserDto) and registers the user.
     * After successful registration, the response will return a "201 Created" status.
     *
     * @param authDto the user information required for registration.
     *                The user must provide details such as username, password, and email.
     * @return a ResponseEntity indicating the success of the registration operation.
     *         A 201 status is returned on successful user registration.
     * @see UserDto
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @RequestBody @Valid UserDto authDto
    ){
        log.info("register user dto: {}", authDto);
        authService.register(authDto);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    /**
     * Logs in an existing user to the system.
     *
     * <p>This endpoint accepts a request body containing login credentials (AuthDto) and authenticates the user.
     * If the credentials are correct, the system will return the user details as a response along with an "OK" status.
     *
     * @param authDto the login credentials (e.g., username and password) of the user.
     * @return a ResponseEntity containing the user details if login is successful.
     *         A 200 OK status is returned along with the user information (UserDto).
     * @see AuthDto
     * @see UserDto
     */
    @PostMapping("/signin")
    public ResponseEntity<?> login(
            @RequestBody AuthDto authDto
    ){
        var userDto = authService.login(authDto);
        userDto.setPassword(authDto.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}

