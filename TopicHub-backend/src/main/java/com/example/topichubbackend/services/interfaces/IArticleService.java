package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

public interface IArticleService {
    void create(ArticleDto articleDto);

    ArticleBatchDto fetch(Integer param, Integer page);
}
