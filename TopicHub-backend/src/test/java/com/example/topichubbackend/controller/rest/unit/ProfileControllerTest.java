package com.example.topichubbackend.controller.rest.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.controller.rest.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {
    @Mock
    private IArticleService articleService;

    @Mock
    private IAuthService authService;

    @Mock
    private IImageService imageService;

    @Mock
    private CustomSecurityExpression customSecurityExpression;

    @InjectMocks
    private ProfileController profileController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    void testDoGetArticles() {
        var id = UUID.randomUUID();
        when(customSecurityExpression.getUserId()).thenReturn(String.valueOf(id));
        var filter = ArticleFilterDto.builder()
                .page(1)
                .hub(14)
                .param(0)
                .userId(id.toString())
                .build();
        var batch = ArticleBatchDto.builder()
                .page(filter.getPage())
                .build();
        when(articleService.fetch(any(ArticleFilterDto.class))).thenReturn(batch);

        var response = profileController.doGet("articles", filter.getPage());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(batch.getPage(), filter.getPage());
        assertTrue(response.hasBody());
    }

    @Test
    void testDoGetProfile(){
        var id = UUID.randomUUID();
        when(customSecurityExpression.getUserId()).thenReturn(String.valueOf(id));
        var filter = ArticleFilterDto.builder()
                .page(1)
                .hub(14)
                .param(0)
                .userId(id.toString())
                .build();
        var user  = UserDto.builder()
                .id(id.toString())
                .email("email@mail.ru")
                .password("123456")
                .build();
        when(authService.findById(id.toString())).thenReturn(user);

        var response = profileController.doGet("profile", filter.getPage());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertTrue(response.hasBody());
    }

    @Test
    void testDoGetBadRequest() {
        var id = UUID.randomUUID();
        when(customSecurityExpression.getUserId()).thenReturn(String.valueOf(id));
        var filter = ArticleFilterDto.builder()
                .page(1)
                .hub(14)
                .param(0)
                .userId(id.toString())
                .build();
        var response=profileController.doGet("bad request", filter.getPage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
