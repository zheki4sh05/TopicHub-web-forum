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

@WebServlet(urlPatterns = {"/subscription"})
public class SubscribeServlet extends HttpServlet{

    private final IReactionService reactionService = ServiceFactory.getReactionService();

    private final CustomValidator customValidator =new CustomValidator();
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            SubscriptionRequestDto subscriptionRequestDto =(SubscriptionRequestDto) JsonMapper.mapFrom(request, SubscriptionRequestDto.class).orElseThrow(RuntimeException::new);
            customValidator.validate(subscriptionRequestDto);
            String userId = (String) request.getAttribute("id");
            reactionService.manageSubscription(1, subscriptionRequestDto.getAuthor(),userId);

        }catch (BadRequestException e){
            response.setStatus(400);
        }
        catch (RuntimeException e){
            response.setStatus(500);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String author = request.getParameter("author");

        if(author==null){
            response.setStatus(400);
        }else{
            String userId = (String) request.getAttribute("id");
            reactionService.manageSubscription(-1, author,userId);
        }

    }
}
