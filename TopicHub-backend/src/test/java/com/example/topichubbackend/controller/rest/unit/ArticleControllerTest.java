package com.example.topichubbackend.controller.rest.unit;

import com.example.topichubbackend.controller.rest.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.impls.*;
import com.example.topichubbackend.services.interfaces.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.test.web.servlet.setup.*;

import java.util.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {
    @Mock
    private IArticleService articleService;

    @Mock
    private ICommentsService commentsService;
    @InjectMocks
    private ArticleController articleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    void testDoGetArticles() throws Exception {

        ArticleBatchDto articleBatchDto = new ArticleBatchDto(); // Заполните объект с необходимыми данными
        when(articleService.fetch(any(ArticleFilterDto.class))).thenReturn(articleBatchDto);

       var result  = mockMvc.perform(get("/api/v1/article")
                        .param("status", "PUBLISH")) // Подставьте нужные параметры
                .andExpect(status().isOk()); // Проверяем статус ответа


    }

    @Test
    void testDoGetComments() throws Exception {
        // Мокируем сервис
        String articleId = "123";
        CommentDto commentDto = new CommentDto(); // Заполните объект с необходимыми данными
        when(commentsService.fetch(articleId)).thenReturn(Collections.singletonList(commentDto));

        mockMvc.perform(get("/api/v1/article/answers")
                        .param("articleId", articleId))
                .andExpect(status().isOk()) // Проверяем статус ответа
                .andExpect(jsonPath("$[0].id").exists()); // Проверка первого комментария
    }

  
}