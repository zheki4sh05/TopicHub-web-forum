package com.example.topichubbackend.servlets;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;


@WebServlet(urlPatterns = {"/profile"})
@MultipartConfig
public class ProfileServlet extends HttpServlet{

    private final IArticleService articleService  = ArticleService.getInstance();
    private final IAuthService authService = ServiceFactory.getAuthService();
    private final IImageService imageService = ServiceFactory.createImageService();
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
                   ArticleBatchDto articleBatchDto = articleService.fetch(ArticlesSource.OWN.type(),page,userId);
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
                    case "author":{
                        try{
                            String id = request.getParameter("id");
                            authService.delete(id, userId);

                        }catch (EntityNotFoundException e){
                            response.getWriter().write(JsonMapper.mapTo(ErrorDto.builder()
                                    .code(404)
                                    .message("Пользователь не найден")
                                    .build()));
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

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId =(String) request.getAttribute("id");
        try {
            UserDto userDto = (UserDto) JsonMapper.mapFrom(request, UserDto.class).orElseThrow(RuntimeException::new);
            authService.updateUser(userDto, userId);
            response.getWriter().write(JsonMapper.mapTo(userDto));
            response.setStatus(200);
        }
        catch (BadRequestException e){
            response.getWriter().write(JsonMapper.mapTo(ErrorDto.builder()
                    .code(409)
                    .message("Пользователь с таким email либо логином уже существует")
                    .build()));
            response.setStatus(409);
        } catch(EntityNotFoundException e){
            response.getWriter().write(JsonMapper.mapTo(ErrorDto.builder()
                    .code(404)
                    .message("Пользователь с таким email не найден")
                    .build()));
            response.setStatus(404);
        } catch (RuntimeException e){ response.getWriter().write(JsonMapper.mapTo(ErrorDto.builder()
                .code(400)
                .message("Неверный формат запроса")
                .build()));
            response.setStatus(400);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String userId = (String)request.getAttribute("id");
        try {
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream fileContent = filePart.getInputStream()) {
                    imageService.save(userId, fileContent);
                }
                catch(IOException e){
                    response.setStatus(400);
                }
            } else {
                response.setStatus(400);
            }
        } catch (ServletException e) {
            response.setStatus(400);
        }
    }
    private Boolean isValid(String type){
        return type.equals("articles");
    }


}
