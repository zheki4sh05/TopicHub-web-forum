package com.example.topichubbackend.dao.impl;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.entity.complaints.*;
import jakarta.persistence.*;

import java.util.*;

public class CommentDao extends AbstractHibernateDao<UUID, Comment> implements CommentRepository {
    public CommentDao(EntityManager entityManager) {
        this.em= entityManager;
    }
    public List<Comment> findAllByArticleId(Long articleId) {
        try{
            String sql = "From Comment c where c.article.id = :articleId";
            TypedQuery<Comment> query = this.em.createQuery(sql, Comment.class);
            query.setParameter("articleId", articleId);
            return query.getResultList();
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }


    @Override
    public Optional<Comment> findById(String targetId) {
        return super.findById(UUID.fromString(targetId));
    }


    public Long calcArticleCommentsCount(Long id) {
        String countQ = "SELECT COUNT(c.id) FROM Comment c WHERE c.article.id= :id";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        countQuery.setParameter("id", id);
        return (Long) countQuery.getSingleResult();
    }
}
