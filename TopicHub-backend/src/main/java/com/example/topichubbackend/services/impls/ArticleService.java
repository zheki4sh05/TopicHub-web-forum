package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

import java.sql.*;
import java.time.*;
import java.util.*;


public class ArticleService implements IArticleService {
    private final static ArticleService articleService = new ArticleService();
    private ArticleService() { }
    public static ArticleService  getInstance(){
        return articleService;
    }
    private final ArticleDao articleDao = DaoFactory.createArticleDao();

    private final String dilimiter = "|";
    @Override
    public void create(ArticleDto articleDto) {

        final Article article = Article.builder()
                .theme(articleDto.getTheme())
                .keyWords(String.join(dilimiter, articleDto.getKeyWords()))
                .likes(0L)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .dislikes(0l)
                .build();

       articleDao.save(article);

       articleDao.merge(article);

       List<ArticlePart> articlePartList = new ArrayList<>();

        articleDto.getList().forEach(item->{

            articleDao.save(ArticlePart.builder()
                    .uuid(UUID.randomUUID())
                    .id(item.getId())
                    .value(item.getValue())
                    .name(item.getName())
                    .type(item.getType())
                    .article(article)
                    .build());
//
//            articlePartList.add(ArticlePart.builder()
//                    .uuid(UUID.randomUUID())
//                    .id(item.getId())
//                    .value(item.getValue())
//                    .name(item.getName())
//                    .type(item.getType())
//                    .build())
            ;
        });


    }
}
