package com.example.topichubbackend.util;

import com.zaxxer.hikari.*;
import jakarta.persistence.*;

import javax.sql.*;
import java.util.*;

public class PersistUtil {

    private static final EntityManagerFactory EMF;

    static {
//        String dbUrl = System.getenv("DB_URL");
//        String dbUsername = System.getenv("DB_USERNAME");
//        String dbPassword = System.getenv("DB_PASSWORD");
//        String dbDriver = System.getenv("DB_DRIVER");
//        if (dbUrl == null || dbUsername == null || dbPassword == null || dbDriver == null) {
//            throw new IllegalStateException("Environment variables for database connection are missing");
//        }
//        Map<String, String> properties = new HashMap<>();
//        properties.put("hibernate.connection.url", dbUrl);
//        properties.put("hibernate.connection.username", dbUsername);
//        properties.put("hibernate.connection.password", dbPassword);
//        properties.put("hibernate.connection.driver_class", dbDriver);
//        EMF =  Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa",properties);
//
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(dbUrl);
//        hikariConfig.setUsername("your_username");
//        hikariConfig.setPassword("your_password");
//        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        hikariConfig.setMaximumPoolSize(10);
//        hikariConfig.setConnectionTestQuery("SELECT 1");
//
//        // Создание DataSource
//        DataSource dataSource = new HikariDataSource(hikariConfig);
//
//        // Настройка EntityManagerFactory
//        java.util.Map<String, Object> properties = new java.util.HashMap<>();
//        properties.put("hibernate.datasource", dataSource);

        EMF =  Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");

    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

}