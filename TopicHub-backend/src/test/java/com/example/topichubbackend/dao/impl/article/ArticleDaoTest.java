package com.example.topichubbackend.dao.impl.article;

import com.example.topichubbackend.dao.impl.*;
import com.example.topichubbackend.dao.impl.user.*;
import com.example.topichubbackend.dao.impl.util.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.util.factories.*;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import static com.example.topichubbackend.services.impls.ArticleService.DILIMITER;
import static org.junit.jupiter.api.Assertions.*;
class ArticleDaoTest {

    static ArticleDao articleDao;
    static ArticlePartDao articlePartDao;
    static UserDao userDao;
    static HubDao hubDao;


    @BeforeAll
    static void initDao(){
        PersistUtilTest.createSession();
        articleDao =new ArticleDao(PersistUtilTest.configureManager());
         articlePartDao =  new ArticlePartDao(PersistUtilTest.configureManager());
         userDao =new UserDao(PersistUtilTest.configureManager());
         hubDao = new HubDao(PersistUtilTest.configureManager());
        User user = EntityFactory.getUserAdmin();
        userDao.save(user);

        Hub hub = Hub.builder()
                .id(1)
                .ruName("хаб")
                .enName("hub")
                .build();

        var savedHub = hubDao.save(hub);

        final Article article = Article.builder()
                .theme("Новая тема")
                .keyWords(String.join(DILIMITER, "1", "2","3"))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .author(user)
                .hub(savedHub)
                .build();

        articleDao.save(article);

        articlePartDao.save(ArticlePart.builder()
                    .uuid(UUID.randomUUID())
                    .id(1)
                    .value("Заголовок")
                    .name(ArticlePartType.CHAPTER.name())
                    .type(ArticlePartType.CHAPTER.name())
                    .created(1L)
                    .article(article)
                    .build());
    }
    @Test
    void getFeed(){
        User user = EntityFactory.getUserAdmin();
        var feed = articleDao.getSortedAndPaginated(ArticleDao.authorArticles, 1, user.getUuid().toString());
        assertEquals(feed.size(), 1);
    }

    @AfterAll
    static void dropDb(){
        PersistUtilTest.dropDb();
    }





}