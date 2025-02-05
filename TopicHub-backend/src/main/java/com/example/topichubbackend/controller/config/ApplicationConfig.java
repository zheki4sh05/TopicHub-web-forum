package com.example.topichubbackend.controller.config;

import com.example.topichubbackend.services.impls.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
