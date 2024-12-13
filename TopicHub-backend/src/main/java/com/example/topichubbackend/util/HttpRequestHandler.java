package com.example.topichubbackend.util;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.servlet.http.*;

import java.util.*;

public class HttpRequestHandler {
    public static String COOKIE_NAME="topichub";

    private final SessionDao sessionDao = DaoFactory.createSessionDao();
    public static Optional<Cookie> getSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(item -> item.getName().equals(COOKIE_NAME)).findFirst();
    }


    public User findUserByCookie(Cookie sessionCookie) {
        UUID uuid = UUID.fromString(sessionCookie.getValue());
        Session session = sessionDao.findById(uuid).orElseThrow(BadRequestException::new);
        return session.getUser();
    }
}
