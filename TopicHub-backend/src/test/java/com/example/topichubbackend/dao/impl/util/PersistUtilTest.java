package com.example.topichubbackend.dao.impl.util;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.entity.Session;
import com.example.topichubbackend.util.*;
import jakarta.persistence.*;
import org.hibernate.*;
import org.hibernate.cfg.*;

import java.io.*;
import java.sql.*;

public class PersistUtilTest {

    private static SessionFactory session;

    public static Configuration getConfig(){
         Configuration configuration = new Configuration();

        // Устанавливаем параметры конфигурации
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.highlight_sql", "true");

        // Настройки подключения через HikariCP
        configuration.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        configuration.setProperty("hibernate.hikari.connectionTimeout", "5000");
        configuration.setProperty("hibernate.hikari.minimumIdle", "5");
        configuration.setProperty("hibernate.hikari.maximumPoolSize", "20");
        configuration.setProperty("hibernate.hikari.idleTimeout", "300000");

        // Устанавливаем параметры подключения к базе данных
        configuration.setProperty("jakarta.persistence.jdbc.url", "jdbc:h2:mem:web_forum_test");
        configuration.setProperty("jakarta.persistence.jdbc.user", "");
        configuration.setProperty("jakarta.persistence.jdbc.password", "");
        configuration.setProperty("jakarta.persistence.jdbc.driver", "org.h2.Driver");

        // Строим и возвращаем SessionFactory
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(UserRole.class);
        configuration.addAnnotatedClass(Hub.class);
        configuration.addAnnotatedClass(Article.class);
        configuration.addAnnotatedClass(Likes.class);
        configuration.addAnnotatedClass(ArticlePart.class);
        configuration.addAnnotatedClass(Session.class);
        return configuration;
    }

    public static void createSession(){
        session = getConfig().buildSessionFactory();
    }

    public static EntityManager configureManager(){
        return session.createEntityManager();
    }
    public static void  dropDb(){
        session.close();
    }
}
