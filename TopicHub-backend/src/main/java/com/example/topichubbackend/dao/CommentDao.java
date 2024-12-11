package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class CommentDao extends BaseDao{
    public CommentDao(EntityManager entityManager) {
        this.em= entityManager;
    }

    public Optional<Comment> findByParentId(String parentId) {

        try{
            String sql = "From Comment c where c.id = :id";

            Query query = this.em.createQuery(sql, Comment.class);
            query.setParameter("id", UUID.fromString(parentId));
            Comment result =(Comment) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }


    }

    public List<Comment> findAllByArticleId(Long articleId) {
        try{

            EntityGraph entityGraph = this.em.getEntityGraph("comment-entity-graph");
            String sql = "From Comment c where c.article.id = :articleId";

            TypedQuery<Comment> query = this.em.createQuery(sql, Comment.class);
            query.setParameter("articleId", articleId);
            query.setHint("jakarta.persistence.fetchgraph", entityGraph);

            return query.getResultList();

        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    public Optional<Comment> findByUuid(String id) {

        try{
            String sql = "From Comment c where c.id = :id";

            Query query = this.em.createQuery(sql, Comment.class);
            query.setParameter("id", UUID.fromString(id));
            Comment result =(Comment) query.getSingleResult();
            return Optional.ofNullable(result);
        }catch (NoResultException e){
            return Optional.empty();
        }

    }
}
