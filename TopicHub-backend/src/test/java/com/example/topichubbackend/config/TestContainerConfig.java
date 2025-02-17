package com.example.topichubbackend.config;

import org.springframework.boot.test.context.*;
import org.springframework.boot.testcontainers.service.connection.*;
import org.springframework.context.annotation.*;
import org.testcontainers.containers.*;

@TestConfiguration
public class TestContainerConfig {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer(){
        return new PostgreSQLContainer<>("postgres:latest");
    }

}
