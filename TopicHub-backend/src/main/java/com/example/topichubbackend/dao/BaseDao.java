package com.example.topichubbackend.dao;




import jakarta.persistence.*;

import java.util.function.*;

public abstract class BaseDao {

    protected EntityManager em;

    public BaseDao() {
    }
//
//
//    public BaseDao(EntityManager em) {
//        this.em = em;
//    }

    //    public BaseDao() {
//        this.em = PersistUtil.getEntityManager();
//    }
    public <T> void merge(T entity) {
        transaction(em -> em.merge(entity));
    }
    public <T> void refresh(T entity) {
        em.refresh(entity);
    }

    protected void transaction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }


    public Object save(Object entity) {
        transaction(em -> em.persist(entity));
        return entity;
    }

    public void delete(Object entity) {
        transaction(em -> em.remove(entity));
    }


}
