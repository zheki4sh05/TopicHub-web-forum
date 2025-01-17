package com.example.topichubbackend.servlets.auth;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.mapper.objectMapper.*;
import com.example.topichubbackend.mapper.objectMapper.impl.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet("/auth/signin")
public class LoginServlet extends HttpServlet{
    private final ISessionService sessionService = ServiceFactory.getSessionService();

    private final IAuthService authService = ServiceFactory.getAuthService();

    private final CustomValidator customValidator = new CustomValidator();

    private final IObjectMapper objectMapper = new ObjectMapperImpl();

    public void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException {

        AuthDto userDto =(AuthDto) JsonMapper.mapFrom(request, AuthDto.class).orElseThrow(RuntimeException::new);
        try {

            customValidator.validate(userDto);
            User user = authService.login(userDto).orElseThrow(UserNotFoundException::new);
            resp.getWriter().write(
                    JsonMapper.mapTo(
                            objectMapper.mapFrom(user,
                                    authService.getUserRole(user.getUuid()))));
            resp.addCookie(createCookie(user));
            resp.setStatus(200);
        } catch (UserNotFoundException | InternalServerErrorException | UserBlockException e) {
            resp.getWriter().write(HttpResponseHandler.error(e));
            resp.setStatus(e.getCode());
        } catch (BadRequestException e){
            resp.getWriter().write(HttpResponseHandler.error(e));
            resp.setStatus(e.getCode());
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        var cookies = HttpRequestHandler.getSessionCookie(request);
        if(cookies.isEmpty()){
            resp.getWriter().write(String.valueOf( 0));
        }else{
            resp.getWriter().write(String.valueOf( 1));
        }
    }
    private Cookie createCookie(User user){
        UUID uuid = sessionService.createByUser(user);

        Cookie cookie = new Cookie(HttpRequestHandler.COOKIE_NAME, uuid.toString());
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");

        return cookie;
    }



}
