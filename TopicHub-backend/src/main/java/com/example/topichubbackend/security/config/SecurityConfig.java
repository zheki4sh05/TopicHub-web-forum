package com.example.topichubbackend.security.config;


import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.filters.*;
import com.example.topichubbackend.services.impls.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.hibernate.jpa.boot.spi.*;
import org.springframework.boot.autoconfigure.orm.jpa.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.*;

import javax.sql.*;
import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
    private final CustomLogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         http
                .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(authorizeRequests->
                         authorizeRequests
                                 .requestMatchers("/api/v1/auth/**").permitAll()
                                 .requestMatchers("/admin/**").hasAuthority(RoleDto.ADMIN.type())
                                 .requestMatchers("/api/v1/logout").permitAll()
                                 .requestMatchers("/api/v1/article").permitAll()
                                 .requestMatchers("/api/v1/article/**").permitAll()
                                 .requestMatchers("/api/v1/answers").permitAll()
                                 .requestMatchers("/api/v1/image").permitAll()
                                 .requestMatchers("/api/v1/hubs").permitAll()
                                 .anyRequest().authenticated()
                 )
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.BAD_REQUEST)))
                .logout(l->l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ));

        return http.build();

    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }






}
