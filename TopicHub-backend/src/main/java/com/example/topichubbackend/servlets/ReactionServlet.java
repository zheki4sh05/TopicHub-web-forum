package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/reaction"})
public class ReactionServlet extends HttpServlet{
    private IReactionService reactionService = ServiceFactory.getReactionService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        String type = request.getParameter("type");
//        String value = request.getParameter("value");
//        String targetId = request.getParameter("id");
//        String email = (String) request.getAttribute("user");
//
//        reactionService.makeReaction(type, value, email,targetId);

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String articleId = request.getParameter("article");
        String authorId = request.getParameter("author");
        String userId = (String)request.getAttribute("id");

        if((articleId==null || authorId==null) ){
            response.setStatus(400);
        }else{
            ReactionDto reactionDto = reactionService.check(articleId,authorId, userId);
            response.getWriter().write(JsonMapper.mapTo(reactionDto));
        }

    }



}
