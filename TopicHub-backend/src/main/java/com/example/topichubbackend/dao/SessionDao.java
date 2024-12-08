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

    public Optional<Session> findByUserId(UUID id) {
        String hql = "FROM Session s WHERE s.user.uuid = :id";

        try {
            return Optional.of(this.em.createQuery(hql, Session.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
