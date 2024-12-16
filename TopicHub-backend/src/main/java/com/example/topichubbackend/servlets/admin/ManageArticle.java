package com.example.topichubbackend.servlets.admin;


import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;


@WebServlet(urlPatterns = {"/admin"})
public class ManageArticle extends HttpServlet {
    private final IAuthService authService = ServiceFactory.getAuthService();
    private final IArticleService articleService = ServiceFactory.getArticleService();
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userId = (String) request.getAttribute("id");
        String targetId = request.getParameter("id");
        if(targetId!=null){
            try{

                List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
                if(userRoles.size()>1){

                    articleService.deleteAdmin(targetId);
                    response.getWriter().write(targetId);
                    response.setStatus(200);
                }else{
                    response.setStatus(401);
                }

           }
            catch (EntityNotFoundException e){
                response.setStatus(404);
            }
        }else{
            response.setStatus(400);
        }

    }


}
