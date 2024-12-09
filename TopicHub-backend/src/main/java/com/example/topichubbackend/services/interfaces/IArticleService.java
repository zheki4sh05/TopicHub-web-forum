package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

public interface IArticleService {
    void create(ArticleDto articleDto, String id);

    ArticleBatchDto fetch(Integer param, Integer page,String userId);

    ArticleBatchDto fetch(String type, Integer page,String userId);

    void delete(String id, String userId);

    ArticleBatchDto search(String author, String theme, String keywords,String userId);
}
