package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/react"})
public class ReactionServlet extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArticleDto newArticle =(ArticleDto) JsonMapper.mapFrom(request, ArticleDto.class).orElseThrow(RuntimeException::new);
        response.setStatus(201);
    }
}
