package com.example.topichubbackend.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebFilter("/*")
public class CORSFilter implements Filter {
    private String front;
    {
         front = System.getenv("FRONT");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        System.out.println("CORSFilter HTTP Request: " + request.getMethod());

        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Origin", "http://"+front+":3000");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Methods", "OPTIONS, GET,HEAD, PUT, POST, DELETE");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Credentials", "true");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;


        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }


        chain.doFilter(request, servletResponse);
    }
}
