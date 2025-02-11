package com.example.topichubbackend.repository;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
@Repository
@Slf4j
public class ArticleRepository {

    @PersistenceContext
    private EntityManager em;
    private final Integer BATCH_SIZE = 15;
    public Long calcTotalEntitiesCount(){
        String countQ = "SELECT COUNT(a.id) FROM Article a";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        return (Long) countQuery.getSingleResult();
    }

    public Integer getPageNumber(Long count, Integer pageSize){
        return (int) (Math.ceil((double) count / pageSize));
    }

    public Integer getLastPageNumber(Long count){

        return getPageNumber(count, BATCH_SIZE);
    }


    public PageResponse<Article> findByQuery(CriteriaQuery<Article> articleCriteriaQuery, Pageable pageable) {
        Query query = em.createQuery(articleCriteriaQuery);
        Long total = calcTotalEntitiesCount();
        EntityGraph entityGraph = em.createEntityGraph("article.articlePartList");
        query.setFirstResult((pageable.getPageNumber()-1)*BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        List<Article> result = query.getResultList();
//        var page  = PageDto.<Article>builder()
//                .content(result)
//                .lastPage(getLastPageNumber(total))
//                .total(total)
//                .pageNumber(pageable.getPageNumber())
//                .build();

        PageResponse<Article> page  = PageResponse.<Article>builder()
                .page(pageable.getPageNumber())
                .maxPage(getLastPageNumber(total))
                .items(result)
                .total(total)
                .build();
        return page;

    }
    //necessary method only for tests
    public Article findById(Long savedId) {
        return em.createQuery("SELECT a FROM Article a WHERE a.id = :id", Article.class)
                .setParameter("id", savedId)
                .getSingleResult();
    }
}
