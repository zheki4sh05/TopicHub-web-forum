package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/reaction"})
public class ReactionServlet extends HttpServlet{
    private final IReactionService reactionService = ServiceFactory.getReactionService();
    private final CustomValidator customValidator = new CustomValidator();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userId = (String) request.getAttribute("id");

            try{
                LikeRequestDto reactionDto = (LikeRequestDto) JsonMapper.mapFrom(request, LikeRequestDto.class).orElseThrow(RuntimeException::new);
                customValidator.validate(reactionDto);
                reactionService.makeReaction(reactionDto.getType(), Integer.valueOf(reactionDto.getValue()), userId,Long.valueOf(reactionDto.getTargetId()));
                response.getWriter().write(JsonMapper.mapTo(reactionDto));
                response.setStatus(200);
            }catch (BadRequestException | NumberFormatException e){
                response.setStatus(400);
            }catch (RuntimeException e){
                response.getWriter().write(e.getMessage());
                response.setStatus(500);
            }

    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String type = request.getParameter("type");
        String targetId = request.getParameter("targetId");
        String userId = (String) request.getAttribute("id");

        if(type==null || targetId==null){
            response.setStatus(400);
        }else{
            try{
                reactionService.removeReaction(type,userId,Long.valueOf(targetId));
                response.getWriter().write(targetId);
                response.setStatus(200);
            }catch (BadRequestException e){
                response.setStatus(400);
            }
        }

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
