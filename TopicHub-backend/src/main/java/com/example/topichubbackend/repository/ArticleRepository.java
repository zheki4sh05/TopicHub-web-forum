package com.example.topichubbackend.repository;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;
@Repository
public class ArticleRepository {


    @PersistenceContext
    private final EntityManager em;

    private final Integer BATCH_SIZE  = 15;

    public ArticleRepository(EntityManager entityManager) {
        this.em = entityManager;
    }


    public Long calcTotalEntitiesCount(){
        String countQ = "SELECT COUNT(a.id) FROM Article a";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        return (Long) countQuery.getSingleResult();
    }

    public Integer getPageNumber(Long count, Integer pageSize){
        return (int) (Math.ceil(count / pageSize));
    }

    public Integer getLastPageNumber(Long count){
        return getPageNumber(count, BATCH_SIZE);
    }


    public PageDto<Article> findByQuery(CriteriaQuery<Article> articleCriteriaQuery, Pageable pageable) {
        Query query = em.createQuery(articleCriteriaQuery);
        Long total = calcTotalEntitiesCount();
        EntityGraph entityGraph = em.createEntityGraph("article.articlePartList");
        query.setFirstResult(pageable.getPageNumber()-1);
        query.setMaxResults(15);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        List<Article> result = query.getResultList();
        return PageDto.<Article>builder()
                .content(result)
                .lastPage(getLastPageNumber(total))
                .total(total)
                .pageNumber(pageable.getPageNumber())
                .build();

    }
}
