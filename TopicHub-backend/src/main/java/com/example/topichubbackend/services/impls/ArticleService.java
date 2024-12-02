package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;

public class ArticleService implements IArticleService {
    private final static ArticleService articleService = new ArticleService();
    private ArticleService() { }
    public static ArticleService  getInstance(){
        return articleService;
    }


    @Override
    public ArticleDto create() {
        return null;
    }
}
