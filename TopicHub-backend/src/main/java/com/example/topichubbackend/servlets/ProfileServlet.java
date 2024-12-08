package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet{

    private final IArticleService articleService  = ArticleService.getInstance();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String source= request.getParameter("source");
        String type= request.getParameter("type");
        Integer page = Integer.valueOf(request.getParameter("page"));

        try {
            ArticleBatchDto articleBatchDto = articleService.fetch(type, page);
            response.getWriter().write(JsonMapper.mapTo(articleBatchDto));
            response.setStatus(200);

        } catch (EntityNotFoundException e) {
            response.getWriter().write(e.getMessage());
            response.setStatus(404);
        }


    }
}
