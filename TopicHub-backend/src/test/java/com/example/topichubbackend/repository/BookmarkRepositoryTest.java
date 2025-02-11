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
class BookmarkRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    HubRepository hubRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;
    UUID id1=UUID.randomUUID();
    UUID id2=UUID.randomUUID();

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
        User user2 = User.builder()
                .uuid(id2)
                .login("follower")
                .email("email2@mail.ru")
                .password("123456")
                .build();
        var savedUser2 = userRepository.save(user2);
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
        bookmarkRepository.deleteAll();
        articleRepo.deleteAll();
        userRepository.deleteAll();
        hubRepository.deleteAll();
    }

    @Test
    void check_that_article_is_bookmark(){
        var article = articleRepo.findById(articleId).orElseThrow();
        var author = userRepository.findById(id1).orElseThrow();
        Bookmark bookmark = Bookmark.builder()
                .id(UUID.randomUUID())
                .article(article)
                .author(author)
                .build();
        bookmarkRepository.save(bookmark);
        var result = bookmarkRepository.checkMarked(id1, articleId);
        var savedBookmark = bookmarkRepository.findByUserIdArticleId(id1, articleId);
        assertTrue(result);
        assertTrue(savedBookmark.isPresent());
        assertEquals(bookmark.getId(), savedBookmark.get().getId());
    }
    @Test
    void check_that_article_is_not_bookmark(){
        var article = articleRepo.findById(articleId).orElseThrow();
        var author = userRepository.findById(id1).orElseThrow();
        Bookmark bookmark = Bookmark.builder()
                .id(UUID.randomUUID())
                .article(article)
                .author(author)
                .build();
        bookmarkRepository.save(bookmark);
        var result = bookmarkRepository.checkMarked(id2, articleId);
        assertFalse(result);
    }

}