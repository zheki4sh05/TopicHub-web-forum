package com.example.topichubbackend.repository;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.model.*;
import jakarta.transaction.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.time.*;
import java.util.*;


@SpringBootTest(classes = TestContainerConfig.class)
class LikeRepositoryTest {


    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    HubRepository hubRepository;

    @Autowired
            LikeRepository likeRepository;
    UUID id1=UUID.randomUUID();

    Long articleId;

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
                .status(StatusDto.MODERATION.type())
                .build();

        var savedArticle = articleRepo.save(article1);
        articleId=  savedArticle.getId();

    }
    @AfterEach
    @Transactional
    void clear(){
        likeRepository.deleteAll();
        articleRepo.deleteAll();
        userRepository.deleteAll();
        hubRepository.deleteAll();
    }

    @Test
    void check_user_like_state(){
        var user  = userRepository.findById(id1).orElseThrow();
        var article = articleRepo.findById(articleId).orElseThrow();
        Likes likes = Likes.builder()
                .uuid(UUID.randomUUID())
                .user(user)
                .state(1)
                .article(article)
                .build();
        likeRepository.save(likes);

        var result = likeRepository.userLikeState(id1, articleId);
        assertEquals(1, result.intValue());



    }

}