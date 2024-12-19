package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import jakarta.persistence.*;
import org.hibernate.exception.*;

import java.util.*;

public class ReactionDao extends BaseDao{

    private final String selectReaction = "From Likes l where l.user.id = :userId and l.article.id = :articleId";
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

    public void reactionComment(Integer value, String userId, Long targetId) {


    }

    public Optional<Likes> findById(Long targetId, UUID uuid) {
        try{
            Query query = this.em.createQuery("From Likes l where l.user.id = :userId and l.article.id = :articleId", Likes.class);
            query.setParameter("userId", uuid);
            query.setParameter("articleId", targetId);
            return  Optional.of((Likes) query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }

    }

    public void updateReaction(Likes item) {
        this.merge(item);
    }

    public void saveReaction(Likes build) {
        this.save(build);
    }

    public void removeArticleReaction(String userId, Long articleId) {
        Query query = this.em.createQuery(selectReaction, Likes.class);
        query.setParameter("userId", UUID.fromString(userId));
        query.setParameter("articleId", articleId);
        var subscription = query.getSingleResult();
        this.delete(subscription);
    }

    public Long[] getLikesAndDislikes(Long articleId) {

        String likesQuery = "SELECT COUNT(*) FROM Likes l WHERE l.article.id = :articleId AND l.state = 1";
        String dislikesQuery = "SELECT COUNT(*) FROM Likes l WHERE l.article.id = :articleId AND l.state = -1";

        Query queryLikes = em.createQuery(likesQuery);
        queryLikes.setParameter("articleId", articleId);
        Long likes =(Long) queryLikes.getSingleResult();

        Query queryDislikes = em.createQuery(dislikesQuery);
        queryDislikes.setParameter("articleId", articleId);
        Long dislikes =(Long) queryDislikes.getSingleResult();

        return new Long[]{likes, dislikes};

    }

    public Integer userLikeState(String userId, Long id) {

        if(userId!=null){
            String query = "SELECT l.state FROM Likes l " +
                    "WHERE l.article.id = :articleId AND l.user.id = :userId";
            try{
                Query q = em.createQuery(query, Integer.class);
                q.setParameter("articleId", id);
                q.setParameter("userId", UUID.fromString(userId));
                return(Integer) q.getSingleResult();

            }catch (NoResultException e){
                return 0;
            }
        }else{
            return 0;
        }




    }
}
