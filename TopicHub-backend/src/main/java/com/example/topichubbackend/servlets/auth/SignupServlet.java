package com.example.topichubbackend.servlets.auth;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.facade.*;
import com.example.topichubbackend.facade.impl.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

import static com.example.topichubbackend.util.HttpRequestHandler.COOKIE_NAME;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet{
    private final IAuthService authService = ServiceFactory.getAuthService();

    private final ISessionService sessionService = ServiceFactory.getSessionService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserDto userDto =(UserDto) JsonMapper.mapFrom(request, UserDto.class).orElseThrow(RuntimeException::new);

        if(isUserValid(userDto)){

                User registeredUser = authService.register(userDto);

                UUID uuid = sessionService.createByUser(registeredUser);

                Cookie cookie = new Cookie(COOKIE_NAME, uuid.toString());
                cookie.setHttpOnly(true);

                response.addCookie(cookie);
                response.getWriter().write(JsonMapper.mapTo(registeredUser));
                response.setStatus(201);
        }else{
            response.getWriter().write("Incorrect user data!");
            response.setStatus(400);
        }


    }

    private boolean isUserValid(UserDto userDto) {

        return !userDto.getLogin().isEmpty() && !userDto.getPassword().isEmpty();

    }

}
