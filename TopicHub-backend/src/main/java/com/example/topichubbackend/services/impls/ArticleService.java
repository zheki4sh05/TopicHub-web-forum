package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

public class ArticleService implements IArticleService {
    private final static ArticleService articleService = new ArticleService();
    private ArticleService() { }
    public static ArticleService  getInstance(){
        return articleService;
    }
    private final ArticleDao articleDao = DaoFactory.createArticleDao();

    @Override
    public ArticleDto create(ArticleDto articleDto) {



        return null;
    }
}
