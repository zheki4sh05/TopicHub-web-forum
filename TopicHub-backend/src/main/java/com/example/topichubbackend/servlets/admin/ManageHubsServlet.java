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

@WebServlet(urlPatterns = {"/hubs"})
public class ManageHubsServlet extends HttpServlet{
    private final IHubService hubService = ServiceFactory.createHubService();
    private final IAuthService authService = ServiceFactory.getAuthService();
    private final CustomValidator customValidator = new CustomValidator();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userId = (String) request.getAttribute("id");
        List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
        if(userRoles.size()>1){
            try{
                HubDto hubDto =(HubDto) JsonMapper.mapFrom(request, HubDto.class).orElseThrow(RuntimeException::new);
                customValidator.validate(hubDto);
                HubDto created =  hubService.create(hubDto);
                response.getWriter().write(JsonMapper.mapTo(created));
            }catch (RuntimeException e){
                response.setStatus(400);
            }
        }else{
            response.setStatus(401);
        }

    }
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = (String) request.getAttribute("id");
        String hubId = (String) request.getAttribute("hubId");
        if(hubId!=null){
            try{
                List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
                if(userRoles.size()>1){
                    hubService.delete(Integer.valueOf(hubId));
                    response.getWriter().write(hubId);
                    response.setStatus(200);
                }else{
                    response.setStatus(401);
                }
            }catch (NumberFormatException e){
                response.setStatus(400);
            }
            catch (EntityNotFoundException e){
                response.setStatus(404);
            }

        }else{
            response.setStatus(400);
        }

    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = (String) request.getAttribute("id");
        List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
        if(userRoles.size()>1){
            try {
                HubDto hubDto = (HubDto) JsonMapper.mapFrom(request, HubDto.class).orElseThrow(RuntimeException::new);
                customValidator.validate(hubDto);
                HubDto updated = hubService.update(hubDto);
                response.getWriter().write(JsonMapper.mapTo(updated));
            }
            catch (EntityNotFoundException e){
                    response.setStatus(404);

            }catch (RuntimeException e){
                response.setStatus(400);
            }
        }else{
            response.setStatus(401);
        }
    }
}
