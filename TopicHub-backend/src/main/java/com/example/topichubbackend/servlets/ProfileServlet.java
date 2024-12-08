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


        try{
            String type= request.getParameter("type");
            if(!isValid(type)){
                response.setStatus(400);
                return;
            }
            Integer page = Integer.valueOf(request.getParameter("page"));

            String result="";

            switch(type){
                case "articles":{
                    String userId =(String) request.getAttribute("id");
                   ArticleBatchDto articleBatchDto = articleService.fetch(userId, ArticlesSource.OWN.type(),page);
                   result = JsonMapper.mapTo(articleBatchDto);
                }
            }

            response.getWriter().write(result);
            response.setStatus(200);


        }catch (NumberFormatException e){
            response.setStatus(400);
        }

        }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String type = request.getParameter("type");
            String userId =(String) request.getAttribute("id");

                switch (type){
                    case "article":{
                        try{
                            String id = request.getParameter("id");
                            articleService.delete(id, userId);
                            response.getWriter().write(id);
                        }catch (EntityNotFoundException e){
                            response.setStatus(404);
                            return;
                        }
                        break;
                    }
                    default: {
                        response.setStatus(400);
                        return;
                    }

                }
                response.setStatus(200);

    }
    private Boolean isValid(String type){
        return type.equals("articles");
    }
}
