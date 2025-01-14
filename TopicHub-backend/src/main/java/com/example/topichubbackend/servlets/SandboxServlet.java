package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/sandbox"})
public class SandboxServlet extends HttpServlet{
    private final IArticleService articleService  = ArticleService.getInstance();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            ArticleDto newArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
            String id = (String)request.getAttribute("id");
            articleService.create(newArticle,id);
            response.setStatus(201);
        }catch (EntityNotFoundException e){
            response.getWriter().write(JsonMapper.mapTo(
                    ResponseEntity.error(HttpServletResponse.SC_NOT_FOUND,
                    "Пользователь не найден")));
            response.setStatus(404);
        }
    }
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            ArticleDto updatedArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
            System.out.println(JsonMapper.mapTo(updatedArticle));
            String id = (String)request.getAttribute("id");
            articleService.update(updatedArticle,id);
            response.setStatus(200);
        }catch (EntityNotFoundException e){
            response.getWriter().write(e.getMessage());
            response.setStatus(404);
        }
    }



}
