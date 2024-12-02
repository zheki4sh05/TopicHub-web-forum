package com.example.topichubbackend.util;

import jakarta.persistence.*;

public class PersistUtil {

    private static final EntityManagerFactory EMF;

    private String nameDefault;

    static {

        EMF =  Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

}