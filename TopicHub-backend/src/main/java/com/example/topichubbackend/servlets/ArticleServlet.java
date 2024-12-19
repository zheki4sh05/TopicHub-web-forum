package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {

    private final IArticleService articleService  = ArticleService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{

            String userId = request.getParameter("userId");
            Integer page = Integer.valueOf(request.getParameter("page"));
            String type = request.getParameter("type");
            ArticleBatchDto articleBatchDto;

            if(type.equals("author")){
                String otherUserId = request.getParameter("otherUserId");
                 articleBatchDto = articleService.fetch(page,userId, otherUserId);
            }else{
                Integer hub = Integer.valueOf(request.getParameter("hub"));
                 articleBatchDto = articleService.fetch(hub,page,userId);

            }
            response.getWriter().write(JsonMapper.mapTo(articleBatchDto));
            response.setStatus(200);
        }
        catch (EntityNotFoundException e){
            response.getWriter().write(e.getMessage());
            response.setStatus(404);
        }
        catch (NumberFormatException e){
            response.setStatus(400);
        }

    }

}