package com.example.topichubbackend.dao.impl.user;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class UserDao extends AbstractHibernateDao<UUID, User> {
    public UserDao(EntityManager entityManager) {
        super.em = entityManager;
    }
    public Optional<User> findByEmailOrLogin(String data) {
        String hql = "FROM User u WHERE u.email = :email or u.login = :login";

        try {
            return Optional.of(this.em.createQuery(hql, User.class)
                    .setParameter("email", data)
                    .setParameter("login", data)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public Optional<User> findById(String id) {
      return super.findById(UUID.fromString(id));
    }
    public List<User> findAll(String id) {
        String hql = "FROM User u where u.id != :id";
        try {
            return this.em.createQuery(hql, User.class)
                    .setParameter("id", UUID.fromString(id))
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

}
