package com.example.topichubbackend.dao.impl.reaction;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;
import org.hibernate.exception.*;

import java.util.*;

public class SubscriptionDao  extends AbstractHibernateDao<UUID, Subscription> {
    public SubscriptionDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Boolean checkSubscribe(String userId, String authorId) {
        Query query = this.em.createQuery("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.author.id = :authorId AND s.follower.id = :subscriberId");
        query.setParameter("authorId", UUID.fromString(authorId));
        query.setParameter("subscriberId", UUID.fromString(userId));
        return (Boolean) query.getSingleResult();
    }
    public void subscribe(User authorId, User userId) {

        try{

            Subscription subscription = Subscription.builder()
                    .id(UUID.randomUUID())
                    .author(authorId)
                    .follower(userId)
                    .build();

            super.save(subscription);

        }catch (ConstraintViolationException constraintViolationException){
            throw new BadRequestException();
        }

    }

    public void unsubscribe(String author, String user) {

        Query query = this.em.createQuery("From Subscription s where s.author.id = :authorId and s.follower.id = :follower", Subscription.class);
        query.setParameter("follower", UUID.fromString(user));
        query.setParameter("authorId", UUID.fromString(author));
        var subscription =(Subscription) query.getSingleResult();
        super.delete(subscription);
    }
    public List<Subscription> findSubscribesById(String id) {

        String hql = "FROM Subscription s where s.follower.id = :id";
        try {
            return this.em.createQuery(hql, Subscription.class)
                    .setParameter("id", UUID.fromString(id))
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    public List<Subscription> findFollowersById(String id) {
        String hql = "FROM Subscription s where s.author.id = :id";
        try {
            return this.em.createQuery(hql, Subscription.class)
                    .setParameter("id", UUID.fromString(id))
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
