package com.example.topichubbackend.security.service;

import com.example.topichubbackend.model.*;
import com.example.topichubbackend.model.User;
import com.example.topichubbackend.security.repository.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.security.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import javax.crypto.*;
import java.util.*;
import java.util.function.*;

@Service
@AllArgsConstructor
@PropertySource("classpath:application.yml")
public class JwtService {

    @Value("${application:security:jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    private final TokenRepository tokenRepository;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        var tokenOptional = tokenRepository.findByAccessToken(token);
        boolean validToken= false;

        if(tokenOptional.isPresent() && !tokenOptional.get().getLoggedOut()){
            validToken = true;
        }
        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

//    public boolean isValidRefreshToken(String token, User user) {
//        String username = extractUsername(token);
//
//        var tokenOptional = tokenRepository.findByAccessToken(token);
//        boolean validToken= false;
//
//        if(tokenOptional.isPresent() && !tokenOptional.get().getLoggedOut()){
//            validToken = true;
//        }
//
//        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
//    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpire);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpire );
    }

    private String generateToken(User user, long expireTime) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
