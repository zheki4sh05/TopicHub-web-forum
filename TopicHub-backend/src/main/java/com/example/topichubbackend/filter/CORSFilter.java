package com.example.topichubbackend.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebFilter("/*")
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;



        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;


        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }


        chain.doFilter(request, servletResponse);
    }
}
