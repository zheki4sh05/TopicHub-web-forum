package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;
import org.hibernate.exception.*;

import java.util.*;

public class ReactionDao extends BaseDao{
    public ReactionDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Boolean checkSubscribe(String userId, String authorId) {

        Query query = this.em.createQuery("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.author.id = :authorId AND s.follower.id = :subscriberId");
        query.setParameter("authorId", UUID.fromString(authorId));
        query.setParameter("subscriberId", UUID.fromString(userId));

        return (Boolean) query.getSingleResult();


    }

    public Boolean checkMarked(String userId, String articleId) {
        Query query = this.em.createQuery("SELECT COUNT(b) > 0 FROM Bookmark b WHERE b.author.id = :authorId AND b.article.id = :articleId");
        query.setParameter("articleId", Integer.valueOf(articleId));
        query.setParameter("authorId",  UUID.fromString(userId));

        return (Boolean) query.getSingleResult();
    }

    public void subscribe(User authorId, User userId) {

            try{

                Subscription subscription = Subscription.builder()
                        .id(UUID.randomUUID())
                        .author(authorId)
                        .follower(userId)
                        .build();

                this.save(subscription);

            }catch (ConstraintViolationException constraintViolationException){
                throw new BadRequestException();
            }

    }

    public void unsubscribe(String author, String user) {

        Query query = this.em.createQuery("From Subscription s where s.author.id = :authorId and s.follower.id = :follower", Subscription.class);
        query.setParameter("follower", UUID.fromString(user));
        query.setParameter("authorId", UUID.fromString(author));
        var subscription = query.getSingleResult();
        this.delete(subscription);
    }

    public void addBookmark(Article article, User user) {

        try{

            Bookmark bookmark = Bookmark.builder()
                    .id(UUID.randomUUID())
                    .article(article)
                    .author(user)
                    .build();

            this.save(bookmark);

        }catch (ConstraintViolationException constraintViolationException){
            throw new BadRequestException();
        }

    }

    public void removeBookmark(String articleId, String userId) {
        Query query = this.em.createQuery("From Bookmark b where b.author.id = :userId and b.article.id = :articleId", Bookmark.class);
        query.setParameter("userId", UUID.fromString(userId));
        query.setParameter("articleId", Integer.valueOf(articleId));
        var subscription = query.getSingleResult();
        this.delete(subscription);
    }
}
