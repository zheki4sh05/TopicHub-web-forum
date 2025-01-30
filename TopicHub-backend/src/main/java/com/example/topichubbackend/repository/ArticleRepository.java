package com.example.topichubbackend.repository;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import jakarta.persistence.*;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class ArticleRepository {

    private final EntityManager em;
    private final Integer BATCH_SIZE  = 15;
    public final static String authorArticles = "FROM Article a JOIN FETCH a.author WHERE a.author.id = :id ORDER BY a.created DESC";
    public final static String bookmarks = "FROM Article a JOIN Bookmark b ON a.id = b.article.id and b.author.id = :id";

    public ArticleRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Article> getSortedAndPaginated(String sql, Integer pageNumber, String id){
        EntityGraph entityGraph = em.createEntityGraph("article.articlePartList");
        Query query = this.em.createQuery(sql, Article.class);
        query.setParameter("id", UUID.fromString(id));
        query.setFirstResult((pageNumber - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        List<Article> results = query.getResultList();
        return results;
    }

    public List<Article> getSortedAndPaginated(ArticleFilterDto articleFilterDto, Integer id){
        EntityGraph entityGraph = em.createEntityGraph("article.articlePartList");
        Query query = this.em.createQuery("FROM Article a JOIN FETCH a.author WHERE a.hub.id = :id ORDER BY a.created DESC", Article.class);
        query.setParameter("id", id);
        query.setFirstResult((articleFilterDto.getPage() - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        return (List<Article>) query.getResultList();

    }

    public List<Article> getSortedAndPaginated(ArticleFilterDto articleFilterDto) {
       FilterQueryFactory filterQueryFactory = new FilterQueryFactory(this.em.getCriteriaBuilder());
        EntityGraph entityGraph = em.createEntityGraph("article.articlePartList");
        TypedQuery<Article> query = em.createQuery(filterQueryFactory.createQuery(articleFilterDto, FilterJoin.DEFAULT, null));
        query.setFirstResult((articleFilterDto.getPage() - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        return query.getResultList();
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

    public List<Article> search(String theme, String keywords) {
       return new ArrayList<>();

    }


    public List<Article> search(String author) {
        Query query = this.em.createQuery("FROM Article a WHERE a.author.login = :login ORDER BY a.created ASC", Article.class);
        query.setParameter("login", author);
        query.setMaxResults(BATCH_SIZE);
        return (List<Article>) query.getResultList();
    }

    public Long calcTotalBookmarksCount(String userId) {
        String countQ = "SELECT COUNT(b.id) FROM Bookmark b where b.author.id= :id";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        countQuery.setParameter("id",UUID.fromString(userId));
        return (Long) countQuery.getSingleResult();
    }

    public Long calcTotalUserArticles(String userId) {
        String countQ = "SELECT COUNT(a.id) FROM Article a where a.author.id= :id";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        countQuery.setParameter("id",UUID.fromString(userId));
        return (Long) countQuery.getSingleResult();
    }


    public void delete(Article article) {
        em.remove(article);
    }


    public Article save(Article article) {
        em.persist(article);
        return article;
    }


    public Long calcTotalEntitiesCountByHub(Integer param) {
        String countQ = "SELECT COUNT(a.id) FROM Article a where a.hub.id= :id";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        countQuery.setParameter("id",param);
        return (Long) countQuery.getSingleResult();
    }

    public Long calcTotalSubscribeEntitiesCount(String userId) {
        String countQ = "SELECT COUNT(a.id) FROM Article a JOIN Subscription s ON s.follower.id = :id and a.author.id = s.author.id";
        Query countQuery = this.em.createQuery(countQ, Long.class);
        countQuery.setParameter("id",UUID.fromString(userId));
        return (Long) countQuery.getSingleResult();
    }

    public List<Article> getSubscribeArticles(ArticleFilterDto articleFilterDto, String userId) {
        FilterQueryFactory filterQueryFactory = new FilterQueryFactory(this.em.getCriteriaBuilder());
        Query query = this.em.createQuery(filterQueryFactory.createQuery(articleFilterDto, FilterJoin.SUBSCRIPTION, UUID.fromString(userId)));
        query.setFirstResult((articleFilterDto.getPage() - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        return query.getResultList();

    }


    public Optional<Article> findById(long l) {
        return Optional.ofNullable(em.find(Article.class, l));
    }


}
