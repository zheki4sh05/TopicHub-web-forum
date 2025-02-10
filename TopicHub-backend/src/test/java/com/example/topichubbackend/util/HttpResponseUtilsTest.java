package com.example.topichubbackend.util;

import com.example.topichubbackend.security.dto.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HttpResponseUtilsTest {

    @Test
    void accessCookie() {
    }

    @Test
    void refreshCookie() {
    }

    @Test
    void createCookie() {
        var name = "topichub";
        HttpResponseUtils httpResponseUtils = new HttpResponseUtils(86400000L, 604800000L, name);

        AuthenticationResponse authResponse = mock(AuthenticationResponse.class);
        when(authResponse.getAccessToken()).thenReturn("accessTokenValue");
        when(authResponse.getRefreshToken()).thenReturn("refreshTokenValue");

        Cookie[] cookies = httpResponseUtils.createCookie(authResponse);
        String cookie1Name = httpResponseUtils.accessCookie();
        String cookie2Name = httpResponseUtils.refreshCookie();

        assertEquals(2, cookies.length);
        assertEquals(cookie1Name, cookies[0].getName());
        assertEquals("accessTokenValue", cookies[0].getValue());
        assertEquals(cookie2Name, cookies[1].getName());
        assertEquals("refreshTokenValue", cookies[1].getValue());
    }

}