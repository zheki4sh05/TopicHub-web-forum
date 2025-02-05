package com.example.topichubbackend.security.service.impl;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.security.dto.*;
import com.example.topichubbackend.security.dto.AuthDto;
import com.example.topichubbackend.security.model.*;
import com.example.topichubbackend.security.repository.*;
import com.example.topichubbackend.security.service.*;
import com.example.topichubbackend.util.*;
import jakarta.servlet.http.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserRoleRepository userRoleRepository;
    @Transactional
    public AuthenticationResponse register(AuthDto authDto) {

        Optional<User> isExist = repository.findByEmailOrLogin(authDto.getLogin());
        if(isExist.isPresent()) {
            return new AuthenticationResponse(null, null,"User already exist",null);
        }
        var user  = userMapper.mapFrom(authDto);
        user = repository.save(user);
        var role = userRoleMapper.mapFrom(user);
        userRoleRepository.save(role);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful",null);

    }

    @Transactional
    public AuthenticationResponse authenticate(AuthDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmailOrLogin(request.getLogin())
                .orElseThrow(()->new EntityNotFoundException());
        UserDto userDto = checkUser(user, request);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponse
                (accessToken, refreshToken, "User login was successful", userDto);

    }
    private UserDto checkUser(User isExist, AuthDto userDto) {
        if (isExist == null) {
            throw new InvalidCredentialsException(ErrorKey.NOT_FOUND.type());
        } else if (isExist.getStatus().equals(StatusDto.BLOCK.type())) {
            throw new UserBlockException();
        } else {
            if (new PasswordEncoderWrapper().matches(userDto.getPassword(), isExist.getPassword())) {
                return userMapper.toDto(isExist);
            } else {
                throw new InvalidCredentialsException(ErrorKey.PASS_INCORRECT.type());
            }
        }
    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getUuid());
        if(validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });
        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = repository.findByEmailOrLogin(username)
                .orElseThrow(()->new EntityNotFoundException());

        if(jwtService.isValid(token, user)) {

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);
            UserDto userDto = userMapper.toDto(user);
            return new ResponseEntity<>(new AuthenticationResponse(accessToken, refreshToken, "New token generated",userDto), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
