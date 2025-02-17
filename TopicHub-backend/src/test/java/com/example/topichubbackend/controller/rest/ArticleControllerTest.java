package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import jakarta.transaction.*;
import lombok.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.web.servlet.*;
import org.testcontainers.junit.jupiter.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest(classes = TestContainerConfig.class)
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    HubRepository hubRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepo articleRepo;

    UUID id1=UUID.randomUUID();
    Integer hubId;




    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    @Transactional
    void setUp() {

        Hub hub = Hub.builder()
                .ruName("Хаб")
                .enName("Hub")
                .build();
        var savedHub = hubRepository.save(hub);
        hubId = savedHub.getId();



        User user1 = User.builder()
                .uuid(id1)
                .login("login2")
                .email("email2@mail.ru")
                .password("123456")
                .build();


        var savedUser1 =  userRepository.save(user1);

        ArticleEntity article1 = ArticleEntity.builder()
                .author(userRepository.findById(id1).orElseThrow(EntityNotFoundException::new))
                .hub(savedHub)
                .theme("тема")
                .keyWords("слова")
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .status(StatusDto.MODERATION.name())
                .build();
        ArticleEntity article2 = ArticleEntity.builder()
                .author(userRepository.findById(id1).orElseThrow(EntityNotFoundException::new))
                .hub(savedHub)
                .theme("тема2")
                .keyWords("слова2")
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .status(StatusDto.MODERATION.name())
                .build();

        var savedArticle = articleRepo.save(article1);
        var savedArticle2 = articleRepo.save(article2);
    }

    @Test
    @SneakyThrows
    void test_fetch(){

        mockMvc.perform(get("/api/v1/article")
                        .param("month", "12")
                        .param("year", "2025")
                        .param("userId", id1.toString())
                        .param("status", StatusDto.MODERATION.name())
                        .param("hub", hubId.toString())
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());

    }



}