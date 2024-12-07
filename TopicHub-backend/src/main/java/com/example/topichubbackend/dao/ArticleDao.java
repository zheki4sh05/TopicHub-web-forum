package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;
import jakarta.transaction.*;

import java.util.*;


public class ArticleDao extends BaseDao{

    private final Integer batchSize  = 15;

    public final static String feedArticles = "FROM Article a ORDER BY a.created ASC";

    public final static String hubArticles = "FROM Article a WHERE a.hub.id = :id ORDER BY a.created ASC";

    public final static String authorArticles = "FROM Article a WHERE a.author.id = :id ORDER BY a.created ASC";
    public ArticleDao(EntityManager entityManager) {
        this.em = entityManager;
    }



    public List<Article> getSortedAndPaginated(String sql, Integer pageNumber, String id){

        Query query = this.em.createQuery(sql, Article.class);
        query.setParameter("id", UUID.fromString(id));
        query.setFirstResult((pageNumber - 1) * batchSize);
        query.setMaxResults(batchSize);
        List<Article> results = query.getResultList();
        return results;
    }

    public List<Article> getSortedAndPaginated(Integer pageNumber, Integer id){

        Query query = this.em.createQuery("FROM Article a WHERE a.hub.id = :id ORDER BY a.created ASC", Article.class);
        query.setParameter("id", id);
        query.setFirstResult((pageNumber - 1) * batchSize);
        query.setMaxResults(batchSize);
        List<Article> results = query.getResultList();
        return results;

    }

    public List<Article> getSortedAndPaginated(Integer page) {
        Query query = this.em.createQuery("FROM Article a ORDER BY a.created ASC", Article.class);
        query.setFirstResult((page - 1) * batchSize);
        query.setMaxResults(batchSize);
        List<Article> results = query.getResultList();
        return results;
    }

    public Long calcTotalEntitiesCount(){
        String countQ = "SELECT COUNT(a.id) FROM Article a";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        Long countResults =(Long) countQuery.getSingleResult();
        return countResults;
    }

    public Integer getPageNumber(Long count, Integer pageSize){
        return (int) (Math.ceil(count / pageSize));
    }

    public Integer getLastPageNumber(Long count){

        return getPageNumber(count, batchSize);

    }

    
    public List<ArticlePart> findByArticleId(Long id){


        String hql = "FROM ArticlePart ap WHERE ap.article.id = :id";
        Query query = this.em.createQuery(hql);
        query.setParameter("id", id);

        try {
            return (List<ArticlePart>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    public Optional<Article> findById(Long id){


        String hql = "FROM Article a WHERE a.id = :id";
        Query query = this.em.createQuery(hql, Article.class);
        query.setParameter("id", id);

        try {
            return Optional.of((Article)query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }



}
