package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.*;

import java.io.*;


@WebServlet(urlPatterns = {"/comment"})
@Slf4j
public class CommentsServlet extends HttpServlet {

    private final ICommentsService commentsService = ServiceFactory.getCommentsService();
    private final CustomValidator customValidator = new CustomValidator();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String userId =(String) request.getAttribute("id");
        try {
            CommentDto commentDto = (CommentDto) JsonMapper.mapFrom(request, CommentDto.class).orElseThrow(RuntimeException::new);
            log.info("new comment: {}", commentDto);
            customValidator.validate(commentDto);
            CommentDto created = commentsService.create(commentDto, userId);
            log.info("created comment: {}", created);
            response.getWriter().write(JsonMapper.mapTo(created));
            response.setStatus(201);
        }
        catch(EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
            log.warn("create comment exception: {}", e.getMessage());
        } catch (RuntimeException e){
            response.setStatus(400);
            log.warn("delete comment exception: {}", e.getMessage());
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId =(String) request.getAttribute("id");
        try {
            CommentDto commentDto = (CommentDto) JsonMapper.mapFrom(request, CommentDto.class).orElseThrow(RuntimeException::new);
            customValidator.validate(commentDto);
            log.info("new comment: {}", commentDto);
            CommentDto created = commentsService.update(commentDto, userId);
            log.info("updated comment: {}", commentDto);
            response.getWriter().write(JsonMapper.mapTo(created));
            response.setStatus(200);
        }
        catch(EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
            log.warn("update comment exception: {}", e.getMessage());
        } catch (RuntimeException e){
            response.setStatus(400);
            log.warn("delete comment exception: {}", e.getMessage());
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId =(String) request.getAttribute("id");
        String commentId =request.getParameter("commentId");
        try {
            log.info("delete comment id: {}", commentId);
           commentsService.delete(commentId,userId);
           response.getWriter().write(commentId);
            response.setStatus(200);
        }
        catch(EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
            log.warn("delete comment exception: {}", e.getMessage());
        } catch (RuntimeException e){
            log.warn("delete comment exception: {}", e.getMessage());
            response.setStatus(400);
        }
    }




}
