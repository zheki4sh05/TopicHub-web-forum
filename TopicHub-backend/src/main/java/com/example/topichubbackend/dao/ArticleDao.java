package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;
import jakarta.persistence.Query;
import org.hibernate.search.mapper.orm.*;
import org.hibernate.search.mapper.orm.session.*;


import java.util.*;




public class ArticleDao extends BaseDao{

    private final Integer BATCH_SIZE  = 15;
    public final static String authorArticles = "FROM Article a JOIN FETCH a.author WHERE a.author.id = :id ORDER BY a.created DESC";
    public final static String bookmarks = "FROM Article a JOIN Bookmark b ON a.id = b.article.id and b.author.id = :id";

    private final SearchSession searchSession;
    public ArticleDao(EntityManager entityManager) {

        this.em = entityManager;
        searchSession= Search.session( entityManager );
    }

    public List<Article> getSortedAndPaginated(String sql, Integer pageNumber, String id){

        Query query = this.em.createQuery(sql, Article.class);
        query.setParameter("id", UUID.fromString(id));
        query.setFirstResult((pageNumber - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        List<Article> results = query.getResultList();
        return results;
    }

    public List<Article> getSortedAndPaginated(Integer pageNumber, Integer id){

        Query query = this.em.createQuery("FROM Article a JOIN FETCH a.author WHERE a.hub.id = :id ORDER BY a.created DESC", Article.class);
        query.setParameter("id", id);
        query.setFirstResult((pageNumber - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        List<Article> results = query.getResultList();
        return results;

    }

    public List<Article> getSortedAndPaginated(Integer page) {

        EntityGraph<Article> entityGraph = this.em.createEntityGraph(Article.class);
        String sql = "FROM Article a JOIN FETCH a.author ORDER BY a.created DESC";
        TypedQuery<Article> query = this.em.createQuery(sql, Article.class);
        query.setFirstResult((page - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        return query.getResultList();
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

        return getPageNumber(count, BATCH_SIZE);

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


    public List<Article> search(String theme, String keywords) {

        var query = searchSession.search(Article.class).where(f -> f.bool()
                .should(f.match().field("theme").matching(theme+"*"))
                .should(f.match().field("keyWords").matching(keywords+"*"))
        );

        return query.fetchAllHits();

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

    public List<Article> getSubscribeArticles(Integer page, String userId) {

        Query query = this.em.createQuery(" FROM Article a JOIN Subscription s ON s.follower.id = :id and a.author.id = s.author.id", Article.class);
        query.setParameter("id", UUID.fromString(userId));
        query.setFirstResult((page - 1) * BATCH_SIZE);
        query.setMaxResults(BATCH_SIZE);
        List<Article> results = query.getResultList();
        return results;

    }
}
