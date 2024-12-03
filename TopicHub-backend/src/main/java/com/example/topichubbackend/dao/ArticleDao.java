package com.example.topichubbackend.dao;

import com.example.topichubbackend.entity.*;
import jakarta.persistence.*;
import jakarta.transaction.*;

import java.util.*;


public class ArticleDao extends BaseDao{
    public ArticleDao(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Transactional
    public void saveAll(List<ArticlePart> articles , Article article) {
        for (int i = 0; i < articles.size(); i++) {
            merge(article);
            ArticlePart articlePart = articles.get(i);
            articlePart.setArticle(article);

            save(articlePart);
            if (i % 20 == 0) {
                this.em.flush();
                this.em.clear();

            }
            }
        }


}
