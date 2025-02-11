package com.example.topichubbackend.repository;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.model.*;
import jakarta.transaction.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = TestContainerConfig.class)
class SubscriptionRepositoryTest {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    HubRepository hubRepository;
    UUID id1=UUID.randomUUID();
    UUID id2=UUID.randomUUID();

    @BeforeEach
    void init(){

        User user1 = User.builder()
                .uuid(id1)
                .login("author")
                .email("email1@mail.ru")
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


    }


    @Test
    void check_user_subscription_exists(){

        var user1 = userRepository.findById(id1).orElseThrow();
        var user2 = userRepository.findById(id2).orElseThrow();

        Subscription subscription = Subscription.builder()
                .id(UUID.randomUUID())
                .follower(user2)
                .author(user1)
                .build();

        subscriptionRepository.save(subscription);

        var result = subscriptionRepository.checkSubscribe(user2.getUuid(), user1.getUuid());
        assertTrue(result);
    }

    @Test
    void check_user_subscription_not_exists(){

        User user3 = User.builder()
                .uuid(UUID.randomUUID())
                .login("follower2")
                .email("email3@mail.ru")
                .password("123456")
                .build();
        var savedUser3 = userRepository.save(user3);


        var user1 = userRepository.findById(id1).orElseThrow();
        var user2 = userRepository.findById(id2).orElseThrow();

        Subscription subscription = Subscription.builder()
                .id(UUID.randomUUID())
                .follower(user2)
                .author(user1)
                .build();

        subscriptionRepository.save(subscription);

        var result = subscriptionRepository.checkSubscribe(user3.getUuid(), user1.getUuid());
        assertFalse(result);
    }

    @AfterEach
    @Transactional
    void clear(){
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }



}