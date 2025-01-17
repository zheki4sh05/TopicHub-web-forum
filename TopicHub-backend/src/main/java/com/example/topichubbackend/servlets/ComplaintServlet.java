package com.example.topichubbackend.servlets;

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

@WebServlet(urlPatterns = {"/complaint"})
public class ComplaintServlet extends HttpServlet {

    private final IComplaintControl complaintControl = ServiceFactory.createComplaintService();
    private final CustomValidator customValidator = new CustomValidator();
    private final IAuthService authService = ServiceFactory.getAuthService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            ComplaintDto complaintDto =(ComplaintDto) JsonMapper.mapFrom(request, ComplaintDto.class).orElseThrow(RuntimeException::new);
            customValidator.validate(complaintDto);
            String userId = (String) request.getAttribute("id");
            complaintControl.create(userId, complaintDto);
            response.setStatus(200);
        }catch (BadRequestException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
        }
        catch (EntityAlreadyExists | EntityNotFoundException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
        }
        catch (RuntimeException e){
            response.setStatus(500);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String userId = (String) request.getAttribute("id");
            List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
            if(userRoles.size()>1){
                String type = request.getParameter("type");
                if(type!=null){
                    List<ComplaintDto> complaintDtoList = complaintControl.findAllByType(type);
                    response.getWriter().write(JsonMapper.mapTo(complaintDtoList));
                    response.setStatus(200);
                }else{
                    response.setStatus(400);
                }
            }else{
                response.setStatus(401);
            }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = (String) request.getAttribute("id");
        List<UserRole> userRoles =authService.getUserRole(UUID.fromString(userId));
        if(userRoles.size()>1){
            String complaintId = request.getParameter("id");
            String type = request.getParameter("type");
            if(complaintId!=null && type!=null) {
                try{
                    complaintControl.deleteById(complaintId,type);
                    response.getWriter().write(complaintId);
                    response.setStatus(200);
                }catch (EntityNotFoundException e){
                    response.setStatus(404);
                }
            }else{
                response.setStatus(400);
            }
        }else{
            response.setStatus(401);
        }
    }

}
