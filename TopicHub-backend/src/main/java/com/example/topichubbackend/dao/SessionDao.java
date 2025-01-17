package com.example.topichubbackend.dao;

import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class SessionDao extends AbstractHibernateDao<UUID, Session> implements SessionRepository {
    public SessionDao(EntityManager entityManager) {
        super.em = entityManager;
        super.persistentClass = Session.class;
    }

    @Override
    public Session save(Session session){
        return super.save(session);
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
