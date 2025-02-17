package com.example.topichubbackend.security.filters;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.service.*;
import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final HttpRequestUtils httpRequestUtils;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(Header.NAME.type());

        if (httpRequestUtils.isPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if(authHeader == null || !authHeader.startsWith(Header.ALIAS.type())) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(Alias.LENGTH);
        String username = jwtService.extractUsername(token);
        securityService.check(username, token, request);
        filterChain.doFilter(request, response);
    }
}
