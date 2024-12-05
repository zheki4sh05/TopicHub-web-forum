package com.example.topichubbackend.util;

import jakarta.servlet.http.*;

import java.util.*;

public class HttpRequestHandler {
    public static String COOKIE_NAME="topichub";

    public static Optional<Cookie> getSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(item -> item.getName().equals(UrlPath.COOKIE_NAME)).findFirst();
    }
}
