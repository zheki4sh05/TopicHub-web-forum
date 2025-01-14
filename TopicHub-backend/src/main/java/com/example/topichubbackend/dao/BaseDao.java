package com.example.topichubbackend.dao;




import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;
import org.hibernate.exception.*;


import java.sql.*;
import java.util.*;
import java.util.function.*;

public abstract class BaseDao {

    protected EntityManager em;

    public BaseDao() {
    }
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

    public void saveAll(List<Object> entities) {
        listTransaction(entities, em::persist);
    }

    public void delete(Object entity) {
        transaction(em -> em.remove(entity));
    }

    private void checkViolationException(ConstraintViolationException e){
        if(Objects.requireNonNull(e.getConstraintName()).equals("email_uniq")){
            throw new SuchEmailAlreadyExistsException();
        }else if(Objects.requireNonNull(e.getConstraintName()).equals("login_uniq")){
            throw new SuchLoginAlreadyExistsException();
        }
    }


    public void deleteAll(List<Object> objectList) {
        listTransaction(objectList, em::remove);
    }

    private void listTransaction(List<Object> objectList, Consumer<Object> action){
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            objectList.forEach(action);
            tx.commit();
        }
        catch(RollbackException e){
            tx.rollback();
            var throwable = e.getCause();

            if(throwable instanceof ConstraintViolationException exception){
                checkViolationException(exception);
            }
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}