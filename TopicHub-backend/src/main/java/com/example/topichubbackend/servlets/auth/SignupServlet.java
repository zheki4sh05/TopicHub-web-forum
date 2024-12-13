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

import java.io.*;

@WebServlet("/auth/signup")
public class SignupServlet extends HttpServlet{
    private final IAuthService authService = ServiceFactory.getAuthService();
    private final CustomValidator customValidator = new CustomValidator();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserDto userDto =(UserDto) JsonMapper.mapFrom(request, UserDto.class).orElseThrow(RuntimeException::new);

        try {
            customValidator.validate(userDto);
            authService.register(userDto);
            response.setStatus(201);
        }
        catch(BadRequestException e){
            response.getWriter().write(JsonMapper.mapTo(ErrorDto.builder()
                            .code(400)
                            .message(e.getMessage())
                    .build()));
            response.setStatus(400);
        }
        catch (SuchEmailAlreadyExistsException | SuchLoginAlreadyExistsException e){
            response.getWriter().write(JsonMapper.mapTo(ErrorDto.builder()
                    .code(409)
                    .message(e.getMessage())
                    .build()));
            response.setStatus(409);
        }


    }

}
