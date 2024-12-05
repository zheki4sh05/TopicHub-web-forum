package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class HubDao extends BaseDao{

    public HubDao(EntityManager entityManager) {
        this.em = entityManager;
    }


    public List<Hub> fetchAll() {

        String hql = "FROM Hub";

        Query query = this.em.createQuery(hql, Hub.class);
        List<Hub> hubList = query.getResultList();
        return hubList;
    }
}
