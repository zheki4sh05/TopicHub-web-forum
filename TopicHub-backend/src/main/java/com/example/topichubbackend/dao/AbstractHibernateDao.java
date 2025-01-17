package com.example.topichubbackend.dao;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;
import lombok.extern.slf4j.*;
import org.hibernate.exception.*;
import java.util.*;
import java.util.function.*;
@Slf4j
public abstract class AbstractHibernateDao<K,T> {

    protected EntityManager em;
    protected Class<T> persistentClass;
    public AbstractHibernateDao() {
    }
    public void update(T entity) {
        transaction(em -> em.merge(entity));
    }

    private void transaction(Consumer<EntityManager> action) {
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


    public T save(T entity) {
        transaction(em -> em.persist(entity));
        return entity;
    }

    public void saveAll(List<T> entities) {
        listTransaction(entities, em::persist);
    }

    public void delete(T entity) {
        transaction(em -> em.remove(entity));
    }

    public Optional<T> findById(K id){
        return Optional.ofNullable(em.find(persistentClass, id));
    }

    private void checkViolationException(ConstraintViolationException e){
        if(Objects.requireNonNull(e.getConstraintName()).equals("email_uniq")){
            throw new SuchEmailAlreadyExistsException();
        }else if(Objects.requireNonNull(e.getConstraintName()).equals("login_uniq")){
            throw new SuchLoginAlreadyExistsException();
        }
        log.warn("constraint exception: {}", e.getConstraintName());
    }

    private void listTransaction(List<T> objectList, Consumer<Object> action){
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            log.debug("start list transaction, item: {}", objectList.size());
            objectList.forEach(action);
            tx.commit();
            log.debug("transaction commit");
        }
        catch(RollbackException e){
            tx.rollback();
            var throwable = e.getCause();
            log.warn("transaction RollbackException exception: {}", e.getMessage());
            if(throwable instanceof ConstraintViolationException exception){
                checkViolationException(exception);
            }
        } catch (RuntimeException e) {
            tx.rollback();
            log.warn("transaction rollback, {}", e.getMessage());
            throw e;
        }
    }
}