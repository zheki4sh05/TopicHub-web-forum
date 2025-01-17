package com.example.topichubbackend.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebFilter("/*")
public class LocaleFilter implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Locale locale = Locale.getDefault();
        String acceptLanguage = ((HttpServletRequest) servletRequest).getHeader("Accept-Language");
        if (acceptLanguage != null) {
            String[] languages = acceptLanguage.split(",");
            for (String language : languages) {
                String[] langParts = language.split(";");
                String languageCode = langParts[0].trim();
                if (languageCode.equalsIgnoreCase("ru")) {
                    locale = new Locale("ru");
                    break;
                } else if (languageCode.equalsIgnoreCase("en")) {
                    locale = new Locale("us");
                    break;
                }
            }
        }
        servletRequest.setAttribute("locale", locale);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
