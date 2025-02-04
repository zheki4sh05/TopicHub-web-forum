package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.filters.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    ArticleRepository articleRepository;
    @Mock ArticlePartRepository articlePartRepository;
    @Mock HubRepository hubDao;
    @Mock UserRepository userRepository;
    @Mock LikeRepository likeRepository;
    @Mock
    ArticleMapper articleMapper;
    @Mock ArticleRepo articleRepo;
    @Mock
    FilterQueryFactory filterQueryFactory;
    @InjectMocks
    ArticleService articleService;

    private List<Hub> hubs;
    private List<ArticlePartDto> partsDto;
    private ArticleDto articleDto;
    private ArticleEntity article;
    private ArticlePart part;
    private UUID id;
    private User user;

    @BeforeEach
    void setUp() {
        hubs = List.of(
                Hub.builder()
                        .id(1)
                        .enName("name")
                        .ruName("имя")
                        .build(),
                Hub.builder()
                        .id(2)
                        .enName("name2")
                        .ruName("имя2")
                        .build()
        );

        partsDto = List.of(
                ArticlePartDto.builder()
                        .type("paragraph")
                        .value("value")
                        .name("Параграф")
                        .build(),
                ArticlePartDto.builder()
                        .type("paragraph")
                        .value("value")
                        .name("Параграф")
                        .build()
        );

        part = ArticlePart.builder().build();

        articleDto = ArticleDto.builder()
                .id(1L)
                .theme("Тема")
                .list(partsDto)
                .keyWords(List.of("слово1", "слово2"))
                .hub(hubs.get(0).getId())
                .build();

        article = ArticleEntity.builder()
                .id(null)
                .theme("Тема")
                .keyWords("слово1|слово2")
                .hub(hubs.get(0))
                .author(user)
                .created(null)
                .build();

        id = UUID.randomUUID();

        user = User.builder()
                .uuid(id)
                .email("email@.ru")
                .login("login")
                .password("123456")
                .build();
    }

    @Test
    void createArticle() {
        when(hubDao.findAll()).thenReturn(hubs);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(articleRepo.save(any(ArticleEntity.class))).thenReturn(article);
        when(articlePartRepository.save(any(ArticlePart.class))).thenReturn(part);

        assertDoesNotThrow(() -> {
            articleService.create(articleDto, id.toString());
        });

        verify(articleRepo).save(any(ArticleEntity.class));
        verify(articlePartRepository, atLeastOnce()).save(any(ArticlePart.class));
    }
    @Test
    void found_and_delete(){

    }
}