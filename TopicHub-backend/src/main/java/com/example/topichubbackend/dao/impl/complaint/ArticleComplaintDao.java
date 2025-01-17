package com.example.topichubbackend.dao.impl.complaint;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.complaints.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

public class ArticleComplaintDao extends AbstractHibernateDao<UUID, ArticleComplaint> {
    public ArticleComplaintDao(EntityManager entityManager) {
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

}
