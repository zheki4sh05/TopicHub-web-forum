package com.example.topichubbackend.dao;

import jakarta.persistence.*;

public class ArticleDao extends BaseDao{
    public ArticleDao(EntityManager entityManager) {
        this.em = entityManager;
    }
}
