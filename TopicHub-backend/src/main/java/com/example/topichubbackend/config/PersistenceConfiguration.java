package com.example.topichubbackend.config;

import com.zaxxer.hikari.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.*;
import org.springframework.transaction.annotation.*;

import javax.sql.*;
import java.util.*;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.topichubbackend.repository")
@EnableTransactionManagement
public class PersistenceConfiguration {
        @Bean
        public DataSource dataSource() {
//            DriverManagerDataSource driver = new DriverManagerDataSource();
//            driver.setDriverClassName(System.getenv("DB_DRIVER"));
//            driver.setUrl(System.getenv("DB_URL"));
//            driver.setUsername(System.getenv("DB_USERNAME"));
//            driver.setPassword(System.getenv("DB_PASSWORD"));
//            driver.setDriverClassName("org.postgresql.Driver");
//            driver.setUrl("jdbc:postgresql://localhost:5432/web_forum");
//            driver.setUsername("postgres");
//            driver.setPassword("lhs22LI=D=");
//            driver.setConnectionProperties(jpaProperties());
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl("jdbc:postgresql://localhost:5432/web_forum");
                config.setUsername("postgres");
                config.setPassword("lhs22LI=D=");
                config.setDriverClassName("org.postgresql.Driver");
                config.setMinimumIdle(4);
                config.setMaximumPoolSize(4);
                return new HikariDataSource(config);
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setJpaVendorAdapter(vendorAdapter);
            factory.setPackagesToScan("com.example.topichubbackend");
            factory.setDataSource(dataSource());
            return factory;
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

            JpaTransactionManager txManager = new JpaTransactionManager();
            txManager.setEntityManagerFactory(entityManagerFactory);
            return txManager;
        }
        @Bean
    public CriteriaBuilder createBuilder(EntityManager entityManager){
            return entityManager.getCriteriaBuilder();
        }
    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("spring.jpa.show-sql", "true");
        properties.setProperty("spring.jpa.properties.hibernate.format_sql", "true");
//        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }

}

