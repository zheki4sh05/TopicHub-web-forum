package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class ReactionDao extends BaseDao{
    public ReactionDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Boolean checkSubscribe(String userId, String authorId) {

        Query query = this.em.createQuery("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.author.id = :authorId AND s.follower.id = :subscriberId");
        query.setParameter("authorId", authorId);
        query.setParameter("subscriberId", userId);

        return (Boolean) query.getSingleResult();


    }

    public Boolean checkMarked(String userId, String articleId) {
        Query query = this.em.createQuery("SELECT COUNT(b) > 0 FROM Bookmark b WHERE b.author.id = :authorId AND b.article.id = :articleId");
        query.setParameter("article", articleId);
        query.setParameter("authorId", userId);

        return (Boolean) query.getSingleResult();


    }
}
