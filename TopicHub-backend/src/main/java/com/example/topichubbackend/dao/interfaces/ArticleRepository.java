package com.example.topichubbackend.dao.interfaces;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;

import java.util.*;

public interface ArticleRepository {
    Article save(Article article);

    ArticlePart save(ArticlePart build);

    Long calcTotalEntitiesCountByHub(Integer param);

    Integer getLastPageNumber(Long totalCount);

    Long calcTotalSubscribeEntitiesCount(String userId);

    List<Article> getSubscribeArticles(ArticleFilterDto articleFilterDto, String userId);

    List<Article> getSortedAndPaginated(ArticleFilterDto articleFilterDto);

    Long calcTotalEntitiesCount();

    Long calcTotalUserArticles(String userId);

    Optional<Article> findById(Long l);

    void delete(Article article);

    List<Article> search(String author);

    List<Article> search(String theme, String keywords);

    Long calcTotalBookmarksCount(String userId);

    List<Article> getSortedAndPaginated(String authorArticles, Integer page, String otherUserId);

    List<Article> getSortedAndPaginated(ArticleFilterDto articleFilterDto, Integer id);

    List<ArticlePart> findByArticleId(Long id);
}
 