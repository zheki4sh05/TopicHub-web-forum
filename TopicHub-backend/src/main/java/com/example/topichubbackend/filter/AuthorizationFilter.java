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
                servletRequest.setAttribute("email", user.getEmail());

                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }
    }
//    private boolean isUserLoggedIn(ServletRequest servletRequest) { Object user  =((HttpServletRequest) servletRequest).getSession().getAttribute("user");
//        return user!=null;
//    }

//    private boolean isSessionActive(ServletRequest servletRequest) {
//
//        Optional<Cookie> cookie = HttpRequestHandler.getSessionCookie((HttpServletRequest) servletRequest);
//        return sessionService.isSessionActiveBy(cookie);
//
//
//    }
    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }
}