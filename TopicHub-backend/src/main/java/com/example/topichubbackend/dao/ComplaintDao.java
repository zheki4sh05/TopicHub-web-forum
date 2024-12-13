package com.example.topichubbackend.dao;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.entity.complaints.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

public class ComplaintDao extends BaseDao{
    public ComplaintDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Optional<ArticleComplaint> findByArticleUserId(@NotEmpty String targetId, String userId) {
        try{
            String sql = "From ArticleComplaint ac where ac.article.id = :article and ac.author.id = :author";

            Query query = this.em.createQuery(sql, ArticleComplaint.class);
            query.setParameter("author", UUID.fromString(userId));
            query.setParameter("article", targetId);
            ArticleComplaint result =(ArticleComplaint) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }

    }

    public Optional<CommentComplaint> findByCommentUserId(String targetId, String userId) {
        try{
            String sql = "From CommentComplaint ac where ac.comment.id = :article and ac.author.id = :author";

            Query query = this.em.createQuery(sql, CommentComplaint.class);
            query.setParameter("author", UUID.fromString(userId));
            query.setParameter("comment", targetId);
            CommentComplaint result =(CommentComplaint) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }


    public List<ArticleComplaint> findAllArticle() {
        try{
            String sql = "From ArticleComplaint ac";
            Query query = this.em.createQuery(sql, ArticleComplaint.class);
            List<ArticleComplaint> result =(List<ArticleComplaint>) query.getResultList();
            return result;
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public List<CommentComplaint> findAllComment() {
        try{
            String sql = "From CommentComplaint  cc";
            Query query = this.em.createQuery(sql, CommentComplaint.class);
            List<CommentComplaint> result =(List<CommentComplaint>) query.getResultList();
            return result;
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public Optional<ArticleComplaint> findByIdArticle(String complaintId) {
        try{
            String sql = "From ArticleComplaint ac where ac.id= :id";
            Query query = this.em.createQuery(sql, ArticleComplaint.class);
            query.setParameter("id", UUID.fromString(complaintId));
            ArticleComplaint result = (ArticleComplaint) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }
    public Optional<CommentComplaint> findByIdComment(String complaintId) {
        try{
            String sql = "From CommentComplaint cc where cc.id= :id";
            Query query = this.em.createQuery(sql, CommentComplaint.class);
            query.setParameter("id", UUID.fromString(complaintId));
            CommentComplaint result = (CommentComplaint) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }
    }
}
