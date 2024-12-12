package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/bookmarks"})
public class BookmarksServlet extends HttpServlet {
    private final IReactionService reactionService = ServiceFactory.getReactionService();
    private final IArticleService articleService= ArticleService.getInstance();

    private final CustomValidator customValidator =new CustomValidator();


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            String userId =(String) request.getAttribute("id");
            Integer page = Integer.valueOf(request.getParameter("page"));
             ArticleBatchDto articleBatchDto = articleService.fetchBookMarks(userId, page);
             response.getWriter().write(JsonMapper.mapTo(articleBatchDto));
            response.setStatus(200);
        }catch (NumberFormatException e){
            response.setStatus(400);
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            BookmarksRequestDto bookmarksRequestDto =(BookmarksRequestDto) JsonMapper.mapFrom(request, BookmarksRequestDto.class).orElseThrow(RuntimeException::new);
            customValidator.validate(bookmarksRequestDto);
            String userId = (String) request.getAttribute("id");
            reactionService.manageBookmarks(1, bookmarksRequestDto.getArticle(),userId);

        }catch (BadRequestException e){
            response.setStatus(400);
        }
        catch (RuntimeException e){
            response.setStatus(500);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String article = request.getParameter("article");

        if(article==null){
            response.setStatus(400);
        }else{
            String userId = (String) request.getAttribute("id");
            reactionService.manageBookmarks(-1, article,userId);
        }

    }
}
