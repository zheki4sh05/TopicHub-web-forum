package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class SessionDao extends BaseDao{
    public SessionDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Optional<Session> findById(UUID uuid) {

        Session session = this.em.find(Session.class, uuid);
        return Optional.of(session);

    }
}
