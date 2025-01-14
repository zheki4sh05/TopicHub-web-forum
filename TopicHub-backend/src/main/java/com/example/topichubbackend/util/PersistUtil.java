package com.example.topichubbackend.util;

import jakarta.persistence.*;

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

        EMF =  Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");

    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

}