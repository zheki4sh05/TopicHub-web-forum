package com.example.topichubbackend.servlets.auth;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private final ISessionService sessionService = ServiceFactory.getSessionService();

    private final IAuthService authService = ServiceFactory.getAuthService();

    public void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException {


        UserDto userDto =(UserDto) JsonMapper.mapFrom(request, UserDto.class).orElseThrow(RuntimeException::new);
        try {

            User user = authService.find(userDto).orElseThrow(UserNotFoundException::new);

            UUID uuid = sessionService.createByUser(user);

            Cookie cookie = new Cookie(HttpRequestHandler.COOKIE_NAME, uuid.toString());

            resp.addCookie(cookie);
            resp.setStatus(200);

        } catch (UserNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (InternalServerErrorException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }


}
