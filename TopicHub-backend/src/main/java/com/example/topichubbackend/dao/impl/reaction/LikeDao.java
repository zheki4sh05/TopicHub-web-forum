package com.example.topichubbackend.dao.impl.reaction;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class LikeDao extends AbstractHibernateDao<UUID, Likes> {
    public LikeDao(EntityManager entityManager) {
        this.em = entityManager;
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
         super.update(item);
     }

     public void saveReaction(Likes build) {
         this.save(build);
     }

     public void removeArticleReaction(String userId, Long articleId) {
         String selectReaction = "From Likes l where l.user.id = :userId and l.article.id = :articleId";
         Query query = this.em.createQuery(selectReaction, Likes.class);
         query.setParameter("userId", UUID.fromString(userId));
         query.setParameter("articleId", articleId);
         var like =(Likes) query.getSingleResult();
         this.delete(like);
     }

     public Long[] getLikesAndDislikes(Long articleId) {

         String likesQuery = "SELECT COUNT(l.id) FROM Likes l WHERE l.article.id = :articleId AND l.state = 1";
         String dislikesQuery = "SELECT COUNT(l.id) FROM Likes l WHERE l.article.id = :articleId AND l.state = -1";

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
