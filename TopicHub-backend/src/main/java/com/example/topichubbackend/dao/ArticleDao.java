package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;
import jakarta.transaction.*;

import java.util.*;


public class ArticleDao extends BaseDao{
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

    public List<Article> getSortedAndPaginated(int pageNumber, int pageSize){

        Query query = this.em.createQuery("FROM article ORDER BY created ASC", Article.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Article> results = query.getResultList();
        this.em.close();
        return results;

    }

    private Long calcTotalEntitiesCount(){
        String countQ = "Select count(a.id) from article a";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        Long countResults =(Long) countQuery.getSingleResult();
        return countResults;
    }

    private Integer getPageNumber(Long count, Integer pageSize){
        return (int) (Math.ceil(count / pageSize));
    }


}
