package com.example.topichubbackend.dao.impl;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class HubDao extends AbstractHibernateDao<Long, Hub> implements HubRepository {
    public HubDao(EntityManager entityManager) {
        this.em = entityManager;
    }
    public List<Hub> fetchAll() {
        String hql = "FROM Hub h";
        Query query = this.em.createQuery(hql, Hub.class);
        query.setHint("org.hibernate.cacheable", true);
        List<Hub> hubList = query.getResultList();
        return hubList;
    }

    @Override
    public Hub save(Hub hub) {
        return super.save(hub);
    }

    public Optional<Hub> findById(Integer hubId) {

        String hql = "FROM Hub h WHERE h.id = :id";
        try {
            return Optional.of(this.em.createQuery(hql, Hub.class)
                    .setParameter("id", hubId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
