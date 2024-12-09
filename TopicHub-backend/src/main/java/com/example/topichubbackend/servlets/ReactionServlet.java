package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/react"})
public class ReactionServlet extends HttpServlet{
    private IReactionService reactionService = ServiceFactory.getReactionService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArticleDto newArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
        response.setStatus(201);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String articleId = request.getParameter("article");
        String authorId = request.getParameter("author");
        String userId = (String)request.getAttribute("user");

        if((articleId==null || authorId==null) ){
            response.setStatus(400);
        }else{
            ReactionDto reactionDto = reactionService.check(articleId,authorId, userId);
            response.getWriter().write(JsonMapper.mapTo(reactionDto));
        }

    }

}
