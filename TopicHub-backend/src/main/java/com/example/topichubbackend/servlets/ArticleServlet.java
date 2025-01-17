package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.*;

import java.io.*;

@WebServlet("/article")
@Slf4j
public class ArticleServlet extends HttpServlet {

    private final IArticleService articleService  = ArticleService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            String userId = request.getParameter("userId");
            String type = request.getParameter("type");
            ArticleFilterDto articleFilterDto = parseFilterParams(request);
            ArticleBatchDto articleBatchDto;
            if(type.equals("author")){
                String otherUserId = request.getParameter("otherUserId");
                log.info("filter dto: {}",articleFilterDto);
                articleBatchDto = articleService.fetch(articleFilterDto,userId,otherUserId);
            }else{
                log.info("filter dto: {}",articleFilterDto);
                articleFilterDto.setParam(Integer.valueOf(request.getParameter("hub")));
                 articleBatchDto = articleService.fetch(articleFilterDto,userId);
            }
            response.getWriter().write(JsonMapper.mapTo(articleBatchDto));
            response.setStatus(200);
        }
        catch (EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
        }
        catch (NumberFormatException e){
            response.setStatus(400);
        }

    }

    private ArticleFilterDto parseFilterParams(HttpServletRequest request) {
        return ArticleFilterDto.builder()
                .month(request.getParameter("month"))
                .year(request.getParameter("year"))
                .rating(request.getParameter("rating"))
                .page(Integer.valueOf(request.getParameter("page")))
                .hub(Integer.valueOf(request.getParameter("hub")))
                .build();
    }

}