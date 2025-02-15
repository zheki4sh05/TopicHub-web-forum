package com.example.topichubbackend.security.config;

import com.example.topichubbackend.security.model.*;
import com.example.topichubbackend.security.repository.*;
import com.example.topichubbackend.security.service.*;
import jakarta.servlet.http.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;

// Можно header-ы в константы, как и 7 в substring
@Configuration
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);
        var tokens = tokenRepository.findAllAccessTokensByUser(storedToken.getUser().getUuid());
        tokenRepository.deleteAll(tokens);

    }
}
