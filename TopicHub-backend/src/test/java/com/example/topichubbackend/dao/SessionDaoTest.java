package com.example.topichubbackend.dao;

import com.example.topichubbackend.dao.impl.*;
import com.example.topichubbackend.dao.impl.user.*;
import com.example.topichubbackend.dao.impl.util.*;
import com.example.topichubbackend.entity.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.*;
import java.util.*;

class SessionDaoTest {

    static UserDao userDao;
    static SessionDao sessionDao;

    @BeforeAll
    static void create(){
        PersistUtilTest.createSession();
        userDao = new UserDao(PersistUtilTest.configureManager());
        sessionDao = new SessionDao(PersistUtilTest.configureManager());
    }

    @Test
    void create_and_find_user_session(){
        User user = EntityFactory.getUserAdmin();
        var saved = userDao.save(user);
        Session session = Session.builder()
                .id(UUID.randomUUID())
                .expiresAt(LocalDateTime.now())
                .user(saved)
                .build();
        sessionDao.save(session);
        Optional<Session> find = sessionDao.findByUserId(saved.getUuid());
        assertTrue(find.isPresent());
        assertEquals(find.get().getUser().getUuid().toString(), saved.getUuid().toString());

    }

    @AfterAll
    static void dropDb(){
        PersistUtilTest.dropDb();
    }

}