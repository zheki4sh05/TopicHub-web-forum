package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

public interface IArticleService {
    void create(ArticleDto articleDto, String id);
//    ArticleBatchDto fetch(ArticleFilterDto param,String userId);
//    ArticleBatchDto fetch(Integer page,String userId);
//    ArticleBatchDto fetch(ArticleFilterDto articleFilterDto, String userId, String otherUserId);
    ArticleBatchDto fetch(ArticleFilterDto articleFilter);
    void delete(String id, String userId);
    ArticleBatchDto search(String author, String theme, String keywords,String userId);
    ArticleBatchDto fetchBookMarks(String userId, Integer page);
    void deleteAdmin(String targetId);
    void update(ArticleDto updatedArticle, String id);
    void update(ArticleStatusDto articleStatusDto);


}
