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


@WebServlet(urlPatterns = {"/comment"})
public class CommentsServlet extends HttpServlet {

    private final ICommentsService commentsService = ServiceFactory.getCommentsService();
    private final CustomValidator customValidator = new CustomValidator();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String userId =(String) request.getAttribute("id");
        try {
            CommentDto commentDto = (CommentDto) JsonMapper.mapFrom(request, CommentDto.class).orElseThrow(RuntimeException::new);
            customValidator.validate(commentDto);

            CommentDto created = commentsService.create(commentDto, userId);
            response.getWriter().write(JsonMapper.mapTo(created));
            response.setStatus(201);
        }
        catch(EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
        } catch (RuntimeException e){
            response.setStatus(400);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId =(String) request.getAttribute("id");
        try {
            CommentDto commentDto = (CommentDto) JsonMapper.mapFrom(request, CommentDto.class).orElseThrow(RuntimeException::new);
            customValidator.validate(commentDto);

            CommentDto created = commentsService.update(commentDto, userId);
            response.getWriter().write(JsonMapper.mapTo(created));
            response.setStatus(200);
        }
        catch(EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
        } catch (RuntimeException e){
            response.setStatus(400);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId =(String) request.getAttribute("id");
        String commentId =request.getParameter("commentId");
        try {
           commentsService.delete(commentId,userId);
           response.getWriter().write(commentId);
            response.setStatus(200);
        }
        catch(EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
        } catch (RuntimeException e){
            response.setStatus(400);
        }
    }




}
