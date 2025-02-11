package com.example.topichubbackend.config;

import com.zaxxer.hikari.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.testcontainers.service.connection.*;
import org.springframework.context.annotation.*;
import org.springframework.test.context.*;
import org.testcontainers.containers.*;

import javax.sql.*;

@TestConfiguration
public class TestContainerConfig {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer(){
       var container =  new PostgreSQLContainer<>("postgres:latest");
        return container;
    }

}
