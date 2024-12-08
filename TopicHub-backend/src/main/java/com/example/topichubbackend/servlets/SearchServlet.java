package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.net.http.*;

@WebServlet(urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    private final IArticleService articleService  = ArticleService.getInstance();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String author = request.getParameter("author");
            String theme = request.getParameter("theme");
            String keywords = request.getParameter("keywords");

            if(author.isEmpty() && (theme==null || theme.isEmpty()) &&  (keywords==null || keywords.isEmpty())){
                response.setStatus(400);
            }else{

                ArticleBatchDto articleBatchDto = articleService.search(author,theme,keywords);
                response.getWriter().write(JsonMapper.mapTo(articleBatchDto));
                response.setStatus(200);
            }
    }
}
