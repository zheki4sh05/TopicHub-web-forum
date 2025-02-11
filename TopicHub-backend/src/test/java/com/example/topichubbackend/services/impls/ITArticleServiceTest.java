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
import org.testcontainers.junit.jupiter.*;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = TestContainerConfig.class)
@AutoConfigureMockMvc
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
    UUID id1 = UUID.randomUUID();

    SearchDto searchDto = new SearchDto();

    @BeforeEach
    @Transactional
    void setUp() {

        searchDto.setTheme("Тема статьи");
        searchDto.setKeywords("слово1");
        searchDto.setAuthor("login");
        searchDto.setUserId(id1.toString());
        searchDto.setArticleFilterDto(ArticleFilterDto.builder()
                        .page(1)
                        .userId(id1.toString())
                .build());

        Hub hub = Hub.builder()
                .ruName("Хаб")
                .enName("Hub")
                .build();
        var savedHub = hubRepository.save(hub);
        hubId = savedHub.getId();

        User user1 = User.builder()
                .uuid(id1)
                .login("login")
                .email("email@mail.ru")
                .password("123456")
                .build();
        var savedUser1 = userRepository.save(user1);

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
        list.add(articlePart);

        ArticleDto article = ArticleDto.builder()
                .id(1L)
                .theme("Тема статьи")
                .keyWords(Arrays.asList("слово1", "слово2"))
                .list(list)
                .likes(0)
                .dislikes(0)
                .created(new Timestamp(System.currentTimeMillis()))
                .hub(hubId)
                .userDto(new UserDto())
                .likeState(1)
                .commentsCount(5)
                .build();

        return   articleService.create(article, id1.toString());
    }

    @Test
    @Transactional
    void create() {

        ArticlePartDto articlePart = ArticlePartDto.builder()
                .id(1)
                .created(System.currentTimeMillis())
                .name("Часть статьи")
                .type("Тип статьи")
                .value("Значение статьи")
                .build();
        var list = new ArrayList<ArticlePartDto>();
        list.add(articlePart);

        ArticleDto article = ArticleDto.builder()
                .id(1L)
                .theme("Тема статьи")
                .keyWords(Arrays.asList("ключевое слово1", "ключевое слово2"))
                .list(list)
                .likes(0)
                .dislikes(0)
                .created(new Timestamp(System.currentTimeMillis()))
                .hub(hubId)
                .userDto(new UserDto())
                .likeState(1)
                .commentsCount(5)
                .build();

       var savedId= saveArticle();

        var result = articleRepository.findById(savedId);
        var parts=articlePartRepository.findByArticleId(savedId);
        assertEquals(result.getTheme(), article.getTheme());
        assertEquals(parts.size(), article.getList().size());
        assertEquals(StatusDto.MODERATION.type(), result.getStatus());
    }

    @Test
    void delete_author_article(){
        var savedId= saveArticle();
        assertDoesNotThrow(()->{
            articleService.delete(savedId.toString(), id1.toString());
        });

        assertThrows(EmptyResultDataAccessException.class, ()->{
            articleRepository.findById(savedId);
        });


    }
    @Test
    void delete_not_author_article(){
        var savedId= saveArticle();
        assertThrows(EntityNotFoundException.class,()->{
            articleService.delete(savedId.toString(), "someid");
        });

        assertDoesNotThrow(()->{
            articleRepository.findById(savedId);
        });
    }

    @Test
    void search_by_theme(){
        var savedId= saveArticle();
        searchDto.setKeywords("");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertEquals(pageResponse.getItems().size(), 1);
        assertEquals(pageResponse.getItems().get(0).getId(),savedId);
    }

    @Test
    void search_by_keywords(){
        var savedId= saveArticle();
        searchDto.setTheme("");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertEquals(1, pageResponse.getItems().size());
        assertEquals(pageResponse.getItems().get(0).getId(),savedId);
    }
    @Test
    void search_by_author(){
        var savedId= saveArticle();
        searchDto.setTheme("");
        searchDto.setKeywords("");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertEquals(1, pageResponse.getItems().size());
        assertEquals(pageResponse.getItems().get(0).getId(),savedId);
    }
    @Test
    void test_search_empty_result(){
        var savedId= saveArticle();
        searchDto.setTheme("тема 1");
        searchDto.setKeywords("слово");
        searchDto.setAuthor("author");
        PageResponse<ArticleDto> pageResponse = articleService.search(searchDto);
        assertEquals(0, pageResponse.getItems().size());
    }

    @Test
    void test_status_update(){
        var savedId= saveArticle();
        ArticleStatusDto articleStatusDto = ArticleStatusDto.builder()
                .id(savedId.toString())
                .status(StatusDto.PUBLISH.type())
                .page(1)
                .build();

        articleService.update(articleStatusDto);

        var result = articleRepository.findById(savedId);

        assertEquals(StatusDto.PUBLISH.type(), result.getStatus());

    }

}