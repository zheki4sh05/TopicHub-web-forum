package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

public interface IArticleService {
    void create(ArticleDto articleDto, String id);

    ArticleBatchDto fetch(Integer param, Integer page);

    ArticleBatchDto fetch(String userId, String type, Integer page);

    void delete(String id, String userId);
}
