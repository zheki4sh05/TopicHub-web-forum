package com.example.topichubbackend.servlets.admin;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/authors"})
public class ManageAuthorServlet extends HttpServlet {

    private final IAuthService authService = ServiceFactory.getAuthService();
    private final CustomValidator customValidator = new CustomValidator();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = (String) request.getAttribute("id");
        List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
        if(userRoles.size()>1){
            List<UserDto> authorDtos = authService.findAll(userId);
            response.getWriter().write(JsonMapper.mapTo(authorDtos));
            response.setStatus(200);
        }else{
            response.setStatus(401);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = (String) request.getAttribute("id");

        List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
        if(userRoles.size()>1){
            try{
                AuthorDto userDto = (AuthorDto) JsonMapper.mapFrom(request, AuthorDto.class).orElseThrow(RuntimeException::new);
                customValidator.validate(userDto);
                authService.manageBlock(userDto.getId());
                response.getWriter().write(userDto.getId());
                response.setStatus(200);
            }catch (EntityNotFoundException e){
                response.setStatus(404);
            }
        }else{
            response.setStatus(401);
        }
    }
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = (String) request.getAttribute("id");
        String authorId = request.getParameter("authorId");
        List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
        if(userRoles.size()>1){
            try{
                authService.delete(authorId);
                response.setStatus(200);
            }catch (EntityNotFoundException e){
                response.setStatus(404);
            }
        }else{
            response.setStatus(401);
        }
    }

}
