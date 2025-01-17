package com.example.topichubbackend.dao.impl.user;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class RoleDao extends AbstractHibernateDao<UUID, Role> {
    public RoleDao(EntityManager entityManager) {
        super.em = entityManager;
    }
    public Role findRoleByType(String type){
        String hql = "FROM Role r WHERE r.name = :name";
        try {
            return this.em.createQuery(hql, Role.class)
                    .setParameter("name", type)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
