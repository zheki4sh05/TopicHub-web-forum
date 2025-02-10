package com.example.topichubbackend.util;


import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.util.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpRequestUtilsTest {
    HttpRequestUtils httpRequestUtils = new HttpRequestUtils();
    @Test
    void check_if_path_is_public(){
        List<String> list = List.of(
                "/api/v1/profile/search?login=admin&email=eugeniy200415@gmail.com",
                "/api/v1/auth",
                "/api/v1/search?theme=Тема&author=Автор&keywords=1",
                "/api/v1/article?page=1&hub=0",
                "/api/v1/answers",
                "/api/v1/image",
                "/api/v1/hubs",
                "/auth"
                );
        for(String s:list){
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            when(request.getServletPath()).thenReturn(s);
            assertTrue(httpRequestUtils.isPublic(request));
        }
        assertEquals(list.size(), PublicPath.values().length);
    }

    @Test
    void check_if_path_is_not_public(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn("/api/v1/profile?type=profile&page=1");
            assertFalse(httpRequestUtils.isPublic(request));


    }

    @Test
    void check_status_is_valid(){
        for(StatusDto statusDto: StatusDto.values()){
            assertTrue(HttpRequestUtils.contains(statusDto.type()));
        }
    }

    @Test
    void check_status_is_not_valid(){
        assertFalse(HttpRequestUtils.contains("some_status"));
    }

    @Test
    void test_params(){
       Map<String,String> map = new HashMap<>();
       map.put("author","author");
        map.put("theme","author");
        map.put("keywords","author");
        var result = HttpRequestUtils.parseSearchParams(map);
        assertEquals(result.getAuthor(), map.get("author"));
        assertEquals(result.getTheme(), map.get("theme"));
        assertEquals(result.getKeywords(), map.get("keywords"));


    }

    @Test
    public void testParseFilterParams() {
        Map<String, String> params = new HashMap<>();
        params.put("month", "12");
        params.put("year", "2025");
        params.put("rating", "5");
        params.put("userId", "12345");
        params.put("status", "DRAFT");
        params.put("hub", "10");
        params.put("page", "2");

        ArticleFilterDto result = HttpRequestUtils.parseFilterParams(params);

        assertEquals("12", result.getMonth().toString());
        assertEquals("2025", result.getYear().toString());
        assertEquals("5", result.getRating());
        assertEquals("12345", result.getUserId());
        assertEquals("DRAFT", result.getStatus());
        assertEquals(10, result.getParam().intValue());
        assertEquals(2, result.getPage());
        assertEquals(10, result.getParam().intValue());
    }
    @Test
    public void testGetClientUrl() {
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils("localhost", "8080");
        String expectedUrl = "http://localhost:8080";
        String actualUrl = httpRequestUtils.getClientUrl();
        assertEquals(expectedUrl, actualUrl);
    }



}