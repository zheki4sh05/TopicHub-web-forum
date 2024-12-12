package com.example.topichubbackend.servlets;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(urlPatterns = {"/image"})
@MultipartConfig
public class ImageServlet extends HttpServlet {

    private final IImageService imageService = ServiceFactory.createImageService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("id");
        if(userId!=null){
            try{
                byte[] imageData =  imageService.fetch(userId);
                response.setContentType("image/jpeg");
                response.setContentType("image/img");
                response.setContentLength(imageData.length);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(imageData);
                outputStream.flush();
                response.setStatus(200);
            }catch (EntityNotFoundException e){
                response.setStatus(404);
            }
        }else{
            response.setStatus(400);
        }
    }
}
