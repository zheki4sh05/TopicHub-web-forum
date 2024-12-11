package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/answers"})
public class ArticleCommentsServlet extends HttpServlet {

    private final ICommentsService commentsService = ServiceFactory.getCommentsService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String articleId = request.getParameter("articleId");
        if(articleId!=null){
            List<CommentDto> commentDtoList = commentsService.fetch(articleId);
            response.getWriter().write(JsonMapper.mapTo(commentDtoList));
            response.setStatus(200);
        }else{
            response.setStatus(400);
        }
    }

}
