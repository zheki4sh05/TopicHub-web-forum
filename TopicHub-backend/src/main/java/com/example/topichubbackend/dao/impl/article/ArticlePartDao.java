package com.example.topichubbackend.dao.impl.article;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;

import java.util.*;

public class ArticlePartDao extends AbstractHibernateDao<UUID, ArticlePart> {
    public ArticlePartDao(EntityManager entityManager) {
        super.em = entityManager;
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
