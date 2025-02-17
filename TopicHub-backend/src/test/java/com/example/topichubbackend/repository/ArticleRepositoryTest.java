package com.example.topichubbackend.repository;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.model.*;
import jakarta.transaction.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = TestContainerConfig.class)
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    HubRepository hubRepository;
    UUID id1=UUID.randomUUID();

    Integer hubId;

    @BeforeEach
    void init(){
                User user1 = User.builder()
                .uuid(id1)
                .login("login45")
                .email("email45@mail.ru")
                .password("123456")
                .build();
        var savedUser1 = userRepository.save(user1);
        Hub hub = Hub.builder()
                .ruName("Хаб")
                .enName("Hub")
                .build();
        var savedHub = hubRepository.save(hub);
        hubId = savedHub.getId();

        ArticleEntity article1 = ArticleEntity.builder()
                .author(userRepository.findById(id1).orElseThrow(EntityNotFoundException::new))
                .hub(savedHub)
                .theme("тема")
                .keyWords("слова")
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .status(StatusDto.MODERATION.name())
                .build();

        var savedArticle = articleRepo.save(article1);
    }
    @AfterEach
    @Transactional
    void clear(){
        articleRepo.deleteAll();
        userRepository.deleteAll();
        hubRepository.deleteAll();
    }

    @Test
    void calc_total_entities(){
        Long result = articleRepository.calcTotalEntitiesCount();
        assertEquals(1, result);
    }

    @Test
    void calcPage(){

        Long count = 30L;

       Integer result = articleRepository.getLastPageNumber(count);
       assertEquals(2, result);

    }

}