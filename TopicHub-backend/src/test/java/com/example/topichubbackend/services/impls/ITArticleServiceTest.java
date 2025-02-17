package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import jakarta.transaction.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.dao.*;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestContainerConfig.class)
@AutoConfigureMockMvc
@Disabled
class ITArticleServiceTest {

    @Autowired
    HubRepository hubRepository;
    @Autowired
    ArticleRepo articleRepo;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticlePartRepository articlePartRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleService articleService;

    Integer hubId;

    Long savedId;
    UUID id1 = UUID.fromString("a904e8b8-9da8-4535-b402-9be0b78b2981");

    SearchDto searchDto = new SearchDto();

    @BeforeEach
    void init(){
        searchDto.setTheme("Влияние искусственного интеллекта на общество: возможности и вызовы");
        searchDto.setKeywords("ИИ");
        searchDto.setAuthor("admin");
        searchDto.setUserId(id1.toString());
        searchDto.setArticleFilterDto(ArticleFilterDto.builder()
                .page(1)
                .userId(id1.toString())
                .build());

        Hub hub = Hub.builder()
                .ruName("Хаб2")
                .enName("Hub2")
                .build();
        var savedHub = hubRepository.save(hub);
        hubId = savedHub.getId();

        User user1 = User.builder()
                .uuid(id1)
                .login("login45")
                .email("email45@mail.ru")
                .password("123456")
                .build();
        var savedUser1 = userRepository.save(user1);
        saveArticle();

    }

    @AfterEach
    @Transactional
    void clear(){
        articlePartRepository.deleteAll();
        articleRepo.deleteAll();
        hubRepository.deleteAll();
        userRepository.deleteAll();
    }

    Long saveArticle(){
        ArticlePartDto articlePart = ArticlePartDto.builder()
                .id(1)
                .created(System.currentTimeMillis())
                .name("Часть статьи")
                .type("Тип статьи")
                .value("Значение статьи")
                .build();
        var list = new ArrayList<ArticlePartDto>();
//        list.add(articlePart);

        ArticleDto article = ArticleDto.builder()
                .theme(searchDto.getTheme())
                .keyWords(Arrays.asList(searchDto.getKeywords(), "слово2"))
                .list(list)
                .likes(0)
                .dislikes(0)
                .created(new Timestamp(System.currentTimeMillis()))
                .hub(hubId)
                .userDto(new UserDto())
                .likeState(1)
                .commentsCount(5)
                .build();
        savedId = articleService.create(article, id1.toString());
        return  savedId;
    }




    @Test
    @Transactional
    void create() {
        var article = articleRepo.findById(savedId).orElseThrow();
        assertEquals(searchDto.getTheme(), article.getTheme());
        assertEquals(StatusDto.MODERATION.name(), article.getStatus());
    }

    @Test
    void delete_author_article(){
        assertDoesNotThrow(()->{
            articleService.delete(savedId.toString(), id1.toString());
        });

        assertThrows(EmptyResultDataAccessException.class, ()->{
            articleRepository.findById(savedId);
        });


    }
    @Test
    void delete_not_author_article(){
        assertThrows(EntityNotFoundException.class,()->{
            articleService.delete(savedId.toString(), "someid");
        });

        assertDoesNotThrow(()->{
            articleRepo.findById(savedId);
        });
    }

    @Test
    void search_by_theme(){

        searchDto.setKeywords("");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertTrue(pageResponse.getItems().size()>0);

    }

    @Test
    void search_by_keywords(){

        searchDto.setTheme("");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertTrue(pageResponse.getItems().size()>0);

    }
    @Test
    void search_by_author(){

        searchDto.setTheme("");
        searchDto.setKeywords("");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertTrue(pageResponse.getItems().size()>0);

    }
    @Test
    void test_search_empty_result(){

        searchDto.setTheme("тема 1");
        searchDto.setKeywords("слово");
        searchDto.setAuthor("author");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertEquals(0, pageResponse.getItems().size());
    }

    @Test
    void test_status_update(){

        ArticleStatusDto articleStatusDto = ArticleStatusDto.builder()
                .id(savedId.toString())
                .status(StatusDto.PUBLISH.name())
                .page(1)
                .build();

        articleService.update(articleStatusDto);

        var result = articleRepository.findById(savedId);

        assertEquals(StatusDto.PUBLISH.name(), result.getStatus());

    }




}