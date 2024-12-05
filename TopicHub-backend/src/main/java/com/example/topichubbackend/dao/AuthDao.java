package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;

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


}
