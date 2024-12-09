package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/comment"})
public class CommentsServlet extends HttpServlet {

    private final ICommentsService commentsService = ServiceFactory.getCommentsService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var type = request.getParameter("type");
        var article = request.getParameter("article");

        List<CommentDto> commentDtoList = commentsService.fetch(type, article);

        response.getWriter().write(JsonMapper.mapTo(commentDtoList));


    }

}
