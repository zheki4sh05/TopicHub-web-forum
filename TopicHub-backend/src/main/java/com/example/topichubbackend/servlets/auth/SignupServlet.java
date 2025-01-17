package com.example.topichubbackend.servlets.auth;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.*;

import java.io.*;
@Slf4j
@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet{
    private final IAuthService authService = ServiceFactory.getAuthService();
    private final CustomValidator customValidator = new CustomValidator();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserDto userDto =(UserDto) JsonMapper.mapFrom(request, UserDto.class).orElseThrow(RuntimeException::new);
        log.info("sign up start:{}", userDto);
        try {
            customValidator.validate(userDto);
            authService.register(userDto);
            response.setStatus(201);
            log.info("sign up success with code:{}", 201);
        }
        catch(BadRequestException e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
            log.warn("sign up warning with code: {}, message: {}", 400, e.getLocalizedMessages());
        }
        catch (EntityAlreadyExists e){
            response.getWriter().write(HttpResponseHandler.error(e));
            response.setStatus(e.getCode());
            log.warn("sign up warn with code: {}, message: {}", e.getCode(), e.getLocalizedMessage());
        }
    }

}
