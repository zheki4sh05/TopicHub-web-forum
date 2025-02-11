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
//        dynamicPropertyRegistry.add("postgresql.driver", container::getDriverClassName);
        return container;
    }

//    @Bean
//    DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer){
//        var hikariDataSource = new HikariDataSource();
//        hikariDataSource.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
//        hikariDataSource.setUsername(postgreSQLContainer.getUsername());
//        hikariDataSource.setPassword(postgreSQLContainer.getPassword());
//        return hikariDataSource;
//    }
}
