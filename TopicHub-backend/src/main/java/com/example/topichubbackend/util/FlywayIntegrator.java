package com.example.topichubbackend.util;

import org.flywaydb.core.*;
import org.flywaydb.core.api.*;
import org.hibernate.boot.*;
import org.hibernate.engine.jdbc.spi.*;
import org.hibernate.engine.spi.*;
import org.hibernate.integrator.spi.*;
import org.hibernate.service.spi.*;

import javax.sql.*;
import java.lang.reflect.*;
import java.sql.*;

public class FlywayIntegrator implements Integrator {
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
                          SessionFactoryServiceRegistry serviceRegistry) {



        final JdbcServices jdbcServices = serviceRegistry.getService(JdbcServices.class);
        Connection connection;
        DataSource dataSource = null;

        try {
            connection = jdbcServices.getBootstrapJdbcConnectionAccess().obtainConnection();
            final Method method = connection != null ? connection.getClass().getMethod("getDataSource", null) : null;
            dataSource = (DataSource) (method != null ? method.invoke(connection, null) : null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | SQLException e) {
            e.printStackTrace();
        }

        Flyway flyway = Flyway.configure()
                .dataSource(System.getenv("DB_URL"),
                        System.getenv("DB_USERNAME"),
                        System.getenv("DB_PASSWORD"))
                .load();

        MigrationInfo migrationInfo = flyway.info().current();
        flyway.migrate();

    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }
}
