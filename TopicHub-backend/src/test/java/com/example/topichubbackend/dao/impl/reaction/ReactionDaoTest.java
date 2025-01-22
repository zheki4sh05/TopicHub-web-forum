package com.example.topichubbackend.dao.impl.reaction;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dao.impl.*;
import com.example.topichubbackend.dao.impl.article.*;
import com.example.topichubbackend.dao.impl.user.*;
import com.example.topichubbackend.dao.impl.util.*;
import com.example.topichubbackend.entity.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.time.*;
import java.util.*;

import static com.example.topichubbackend.services.impls.ArticleService.DILIMITER;


class ReactionDaoTest {

    static LikeDao likeDao;
    static BookmarkDao bookmarkDao;
    static SubscriptionDao subscriptionDao;
    static UserDao userDao;
    static HubDao hubDao;
    static ArticleDao articleDao;
    @BeforeAll
    static void create(){
        PersistUtilTest.createSession();
        likeDao = new LikeDao(PersistUtilTest.configureManager());
        bookmarkDao = new BookmarkDao(PersistUtilTest.configureManager());
        subscriptionDao = new SubscriptionDao(PersistUtilTest.configureManager());
        userDao = new UserDao(PersistUtilTest.configureManager());
        hubDao = new HubDao(PersistUtilTest.configureManager());
        articleDao = new ArticleDao(PersistUtilTest.configureManager());
    }

    @AfterAll
    static void dropDb(){
        PersistUtilTest.dropDb();

    }

    @Test
    void add_and_remove_like(){
        User user = EntityFactory.getUserAdmin();
        var saved = userDao.save(user);

        var hub  = Hub.builder()
                .enName("car")
                .ruName("машина")
                .build();
        var savedHub = hubDao.save(hub);
        final Article article = Article.builder()
                .theme("Новая тема")
                .keyWords(String.join(DILIMITER, "1", "2","3"))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .author(saved)
                .hub(savedHub)
                .build();

        var savedArticle = articleDao.save(article);
        Likes likes = Likes.builder()
                .user(saved)
                .uuid(UUID.randomUUID())
                .state(1)
                .article(article)
                .build();
        likeDao.save(likes);

        var find = likeDao.findById(article.getId(), saved.getUuid());
        assertTrue(find.isPresent());
        assertEquals(find.get().getUuid().toString(), likes.getUuid().toString());
        assertEquals(savedArticle.getId(), find.get().getArticle().getId());

    }


}