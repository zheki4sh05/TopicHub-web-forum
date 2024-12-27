package com.example.topichubbackend.servlets.auth;

import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {

    private final ISessionService sessionService = ServiceFactory.getSessionService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Optional<Cookie> authCookie = HttpRequestHandler.getSessionCookie(request);
        authCookie.ifPresent(cookie -> sessionService.delete(cookie.getValue()));
        var cookies = HttpRequestHandler.getSessionCookie(request);
        cookies.ifPresent(cookie -> cookie.setMaxAge(0));

    }

}
