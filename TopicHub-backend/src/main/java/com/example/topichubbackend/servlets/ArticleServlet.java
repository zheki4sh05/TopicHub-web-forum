package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/article"})
public class ArticleServlet extends HttpServlet {

    private final IArticleService articleService  = ArticleService.getInstance();

//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//            ArticleDto newArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
//
//           String email  =  request.getParameter("email");
//
//            articleService.create(newArticle);
//        response.setStatus(201);
//    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer param = Integer.valueOf(request.getParameter("type"));
        Integer page = Integer.valueOf(request.getParameter("page"));

        if(param!=null && page!=null){

            try{
                ArticleBatchDto articleBatchDto = articleService.fetch(param,page);
                response.getWriter().write(JsonMapper.mapTo(articleBatchDto));
                response.setStatus(200);

            }catch (EntityNotFoundException e){
                response.getWriter().write(e.getMessage());
                response.setStatus(404);
            }


        }else{
            response.setStatus(400);
        }

    }

}

//    ArticleDto articleDto = articleService.create(newArticle);
//            response.getWriter().write(JsonMapper.mapTo(articleDto));
