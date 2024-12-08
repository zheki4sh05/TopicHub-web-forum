package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;

import java.util.*;

public class AuthDao extends BaseDao{
    public AuthDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public User findByEmail(String email){

        String hql = "FROM User u WHERE u.email = :email";

        try {
            return this.em.createQuery(hql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }


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


    public void register(User newUser, List<UserRole> userRoles) {
           var list =  new ArrayList<Object>(userRoles);
           list.add(0, newUser);
            this.saveAll(list);

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
