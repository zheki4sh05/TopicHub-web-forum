package com.example.topichubbackend.dao.impl.user;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class UserRoleDao extends AbstractHibernateDao<UUID, UserRole> {
    public UserRoleDao(EntityManager entityManager) {
        super.em = entityManager;
    }
    public List<UserRole> findUserRole(UUID id) {
        String hql = "FROM UserRole ur WHERE ur.user.uuid = :id";

        try {
            return this.em.createQuery(hql, UserRole.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (NoResultException e) {
            throw new RuntimeException();
        }
    }
}
