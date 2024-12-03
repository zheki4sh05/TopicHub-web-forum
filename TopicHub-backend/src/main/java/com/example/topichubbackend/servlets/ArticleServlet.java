package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/article"})
public class ArticleServlet extends HttpServlet {

    private IArticleService articleService  = ArticleService.getInstance();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

            ArticleDto newArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
            articleService.create(newArticle);
        response.setStatus(201);
    }
//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        ArticleDto newArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
//        articleService.create(newArticle);
//        response.setStatus(201);
//    }

}

//    ArticleDto articleDto = articleService.create(newArticle);
//            response.getWriter().write(JsonMapper.mapTo(articleDto));
