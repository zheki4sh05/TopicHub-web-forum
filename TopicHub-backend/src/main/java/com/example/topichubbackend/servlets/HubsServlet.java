package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/article/hubs"})
public class HubsServlet extends HttpServlet {

    private final IHubService hubService = ServiceFactory.createHubService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<HubDto> hubDtoList = hubService.findAll();
        response.getWriter().write(JsonMapper.mapTo(hubDtoList));
    }
}
