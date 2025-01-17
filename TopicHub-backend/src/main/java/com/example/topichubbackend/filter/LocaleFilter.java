package com.example.topichubbackend.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebFilter("/*")
public class LocaleFilter implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    }
}
