package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/sandbox"})
public class SandboxServlet extends HttpServlet{
    private IArticleService articleService  = ArticleService.getInstance();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArticleDto newArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
        String id = (String)request.getAttribute("id");
        articleService.create(newArticle,id);
        response.setStatus(201);
    }
}
