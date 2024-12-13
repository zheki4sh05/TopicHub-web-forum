package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class HubDao extends BaseDao{

    public HubDao(EntityManager entityManager) {
        this.em = entityManager;
    }


    public List<Hub> fetchAll() {

        String hql = "FROM Hub h";

        Query query = this.em.createQuery(hql, Hub.class);
        List<Hub> hubList = query.getResultList();
        return hubList;
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
