package com.example.topichubbackend.filter;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private final ISessionService sessionService = ServiceFactory.getSessionService();

    private final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
    private static final Set<String> PUBLIC_PATH;
    static {
        PUBLIC_PATH = new HashSet<>();
        PUBLIC_PATH.add(UrlPath.LOGIN);
        PUBLIC_PATH.add(UrlPath.REGISTRATION);
        PUBLIC_PATH.add(UrlPath.ARTICLE);
        PUBLIC_PATH.add(UrlPath.SEARCH);
        PUBLIC_PATH.add(UrlPath.ANSWERS);
        PUBLIC_PATH.add(UrlPath.IMAGE);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if(isPublicPath(uri)) {
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            Optional<Cookie> cookie = HttpRequestHandler.getSessionCookie((HttpServletRequest) servletRequest);
            if(sessionService.isSessionActiveBy(cookie)){
                User user = httpRequestHandler.findUserByCookie(cookie.get());
                servletRequest.setAttribute("id", user.getUuid().toString());
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }
    }
    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }
}