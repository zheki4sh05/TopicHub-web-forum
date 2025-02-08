package com.example.topichubbackend.controller.mvc;


import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.dto.*;
import com.example.topichubbackend.security.service.impl.*;
import com.example.topichubbackend.util.*;
import jakarta.servlet.http.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/auth")
@Controller
@Slf4j
public class MVCAuthControllerView {


    private final AuthenticationServiceImpl authenticationService;

    private final HttpResponseUtils httpResponseUtils;
    private final HttpRequestUtils httpRequestUtils;
    @GetMapping("")
    public String welcome(
    ){
         return "admin/login";
    }


    @PostMapping("")
    public String welcome(
            @ModelAttribute("auth") AuthDto authDto,
            HttpServletResponse httpServletResponse
            ){
       AuthenticationResponse authenticationResponse =  authenticationService.authenticate(authDto);
       UserDto user = authenticationResponse.getUserDto();
       if(user.getRoles().contains(RoleDto.ADMIN.type())){
           Cookie[] cookies = httpResponseUtils.createCookie(authenticationResponse);
           httpServletResponse.addCookie(cookies[0]);
           httpServletResponse.addCookie(cookies[1]);
           return "admin/index";
       }else{
           return "redirect:"+ httpRequestUtils.getClientUrl();
       }

    }






}
