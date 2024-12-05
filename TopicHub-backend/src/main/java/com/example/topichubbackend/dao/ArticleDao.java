package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;


public class ArticleDao extends BaseDao{

    private final Integer batchSize  = 15;
    public ArticleDao(EntityManager entityManager) {
        this.em = entityManager;
    }

//    @Transactional
//    public void saveAll(List<ArticlePart> articles , Article article) {
//        for (int i = 0; i < articles.size(); i++) {
//            merge(article);
//            ArticlePart articlePart = articles.get(i);
//            articlePart.setArticle(article);
//
//            save(articlePart);
//            if (i % 20 == 0) {
//                this.em.flush();
//                this.em.clear();
//
//            }
//            }
//        }

    public List<Article> getSortedAndPaginated(Integer pageNumber, Integer id){

        Query query = this.em.createQuery("FROM Article a WHERE a.hub.id = :id ORDER BY a.created ASC", Article.class);
        query.setParameter("id", id);
        query.setFirstResult((pageNumber - 1) * batchSize);
        query.setMaxResults(batchSize);
        List<Article> results = query.getResultList();
        this.em.close();
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



}
