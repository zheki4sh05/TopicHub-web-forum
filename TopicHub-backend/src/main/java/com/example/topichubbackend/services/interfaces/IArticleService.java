package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

public interface IArticleService {
    void create(ArticleDto articleDto, String id);

    ArticleBatchDto fetch(Integer param, Integer page,String userId);

    ArticleBatchDto fetch(Integer page,String userId);

    void delete(String id, String userId);

    ArticleBatchDto search(String author, String theme, String keywords,String userId);

    ArticleBatchDto fetchBookMarks(String userId, Integer page);

    ArticleBatchDto fetch(Integer page, String userId, String otherUserId);

    void deleteAdmin(String targetId);
}
