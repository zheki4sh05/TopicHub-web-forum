package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@AllArgsConstructor
@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ArticlePartRepository articlePartRepository;
    private final HubRepository hubDao;

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private final ArticleMapper articleMapper;

    private final CommentRepository commentRepository;

    public static final String DILIMITER = "|";
    @Override
    @Transactional
    public void create(ArticleDto articleDto, String id) {
        List<Hub> hubList = hubDao.findAll();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
        final Article article = Article.builder()
                .theme(articleDto.getTheme())
                .keyWords(String.join(DILIMITER, articleDto.getKeyWords()))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .author(user)
                .hub(hubList.stream().filter(item->item.getId().equals(articleDto.getHub())).findFirst().orElseThrow())
                .build();

        articleRepository.save(article);

        articleDto.getList().forEach(item->{
            articlePartRepository.save(ArticlePart.builder()
                    .uuid(UUID.randomUUID())
                    .id(item.getId())
                    .value(item.getValue())
                    .name(item.getName())
                    .type(item.getType())
                    .created(item.getCreated())
                    .article(article)
                    .build());
        });

    }

    @Override
    public ArticleBatchDto fetch(ArticleFilterDto filterDto, String userId) {
        switch (filterDto.getParam()) {
            case 0 -> {
                return fetchFeed(filterDto, userId);
            }
            case -1 -> {
                return fetchFromUserSubscribes(filterDto, userId);
            }
            default -> {
                return fetchFromHubs(filterDto, userId);
            }
        }
    }

    private ArticleBatchDto fetchFromHubs(ArticleFilterDto articleFilterDto, String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        List<Hub> hubList = hubDao.findAll();
        Optional<Hub> result = hubList.stream().filter(item->item.getId().equals(articleFilterDto.getParam())).findFirst();
        result.ifPresentOrElse(
                (item)->{
                    Long totalCount = articleRepository.calcTotalEntitiesCountByHub(articleFilterDto.getParam());
                    Integer pageCount = articleRepository.getLastPageNumber(totalCount);
                    articleBatchDto.setPageCount(pageCount);
                    articleBatchDto.setArticleDtoList(getArticleByType(result.get().getId(),articleFilterDto,userId));

                },
                ()->{ throw new EntityNotFoundException("No hub with such name!");
                });

        return articleBatchDto;
    }

    private ArticleBatchDto fetchFromUserSubscribes(ArticleFilterDto articleFilterDto, String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        if(userId==null){
            articleBatchDto.setPageCount(0);
            articleBatchDto.setArticleDtoList(new ArrayList<>());
        }else{
            Long totalCount = articleRepository.calcTotalSubscribeEntitiesCount(userId);
            Integer pageCount = articleRepository.getLastPageNumber(totalCount);
            articleBatchDto.setPageCount(pageCount);
            articleBatchDto.setArticleDtoList(getSubscribesArticles(articleFilterDto,userId));
        }
        return articleBatchDto;

    }

    private List<ArticleDto> getSubscribesArticles(ArticleFilterDto articleFilterDto, String userId) {

        List<Article> articles = articleRepository.getSubscribeArticles(articleFilterDto,userId);
        return doMapping(articles,userId);

    }

    private ArticleBatchDto fetchFeed(ArticleFilterDto filterDto, String userId) {
        ArticleBatchDto articleBatchDto = createBatch();
        articleBatchDto.setArticleDtoList(getFeed(filterDto,userId));
        return articleBatchDto;
    }

    private List<ArticleDto> getFeed(ArticleFilterDto articleFilterDto, String userId) {
       List<Article> articles = articleRepository.getSortedAndPaginated(articleFilterDto);
        return doMapping(articles,userId);
    }

    private  ArticleBatchDto createBatch(){
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleRepository.calcTotalEntitiesCount();
        Integer pageCount = articleRepository.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        return articleBatchDto;
    }

    @Override
    public ArticleBatchDto fetch(Integer page,String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleRepository.calcTotalUserArticles(userId);
        Integer pageCount = articleRepository.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        articleBatchDto.setArticleDtoList(getOwnArticles(page, userId));
        return articleBatchDto;
    }

    @Override
    public void delete(String id, String userId) {
        Article article = articleRepository.findById(Long.parseLong(id)).orElseThrow(EntityNotFoundException::new);
        if(article.getAuthor().getUuid().toString().equals(userId)){
            articleRepository.delete(article);
        }else{
            throw new EntityNotFoundException("Not found article for user");
        }
    }

    @Override
    public ArticleBatchDto search(String author, String theme, String keywords, String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        List<Article> articles = new ArrayList<>();

        if(!author.isEmpty() && theme.isEmpty() && keywords.isEmpty() ){

            articles.addAll(articleRepository.search(author));
            articleBatchDto.setArticleDtoList(doMapping(articles,userId));
        }else if(!author.isEmpty()){
            articles.addAll(articleRepository.search(theme,  keywords));
            var filtered = articles.stream()
                    .filter(item->item.getAuthor().getLogin()
                            .startsWith(author));

            articleBatchDto.setArticleDtoList(doMapping(filtered.collect(Collectors.toList()),userId));
        }else{
            articles.addAll(articleRepository.search(theme,  keywords));
            articleBatchDto.setArticleDtoList(doMapping(articles,userId));

        }
        return articleBatchDto;
    }

    @Override
    public ArticleBatchDto fetchBookMarks(String userId, Integer page) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleRepository.calcTotalBookmarksCount(userId);
        Integer pageCount = articleRepository.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        articleBatchDto.setArticleDtoList(getBookmarks(page, userId));
        return articleBatchDto;
    }

    @Override
    public ArticleBatchDto fetch(ArticleFilterDto articleFilterDto, String userId, String otherUserId) {

        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleRepository.calcTotalUserArticles(otherUserId);
        Integer pageCount = articleRepository.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        articleBatchDto.setArticleDtoList(gteOtherArticles(articleFilterDto.getPage(), userId,otherUserId));
        return articleBatchDto;
    }

    @Override
    public ArticleBatchDto fetch(ArticleFilterDto articleFilterDto, Integer page) {
        return null;
    }

    @Override
    public ArticleBatchDto fetch(ArticleFilterDto articleFilter) {



        return null;
    }

    @Override
    public void deleteAdmin(String targetId) {
        Article article = articleRepository.findById(Long.parseLong(targetId)).orElseThrow(EntityNotFoundException::new);
        articleRepository.delete(article);
    }

    @Override
    public void update(ArticleDto updatedArticle, String id) {
        Article delarticle = articleRepository.findById(updatedArticle.getId()).orElseThrow(()->new EntityNotFoundException("Статья не найдена"));
        articleRepository.delete(delarticle);
        create(updatedArticle, id);
    }


    @Override
    public void update(ArticleStatusDto articleStatusDto) {

    }

    private List<ArticleDto> gteOtherArticles(Integer page, String userId, String otherUserId) {
        List<Article> articles = articleRepository.getSortedAndPaginated(ArticleRepository.authorArticles, page,otherUserId );
        return doMapping(articles,userId);

    }
    private List<ArticleDto> getBookmarks(Integer page, String userId) {
        List<Article> articles = articleRepository.getSortedAndPaginated(ArticleRepository.bookmarks, page,userId );
        return doMapping(articles,userId);

    }
    private List<ArticleDto> getArticleByType(Integer id, ArticleFilterDto articleFilterDto, String userId) {
        List<Article> articles = articleRepository.getSortedAndPaginated(articleFilterDto,id);
        return  doMapping(articles,userId);
    }

    private List<ArticleDto> getOwnArticles(Integer page, String userId){
        List<Article> articles = articleRepository.getSortedAndPaginated(ArticleRepository.authorArticles, page,userId );
        return doMapping(articles,userId);
    }

    private List<ArticleDto> doMapping(List<Article> articles,String userId){
        List<ArticleDto> articleDtos = new ArrayList<>();
        articles.forEach(item->{
            item.setArticlePartList(item.getArticlePartList());
            ArticleDto articleDto = getLikesAndDislikes(item, userId);
            articleDto.setCommentsCount(getCommentsCount(item.getId()));
            articleDtos.add(articleDto);
        });
        return articleDtos;
    }

    private Long getCommentsCount(Long id) {
        return commentRepository.calcArticleCommentsCount(id);
    }

    private ArticleDto getLikesAndDislikes(Article item, String userId){
        var dto = articleMapper.toDto(item);
        dto.setLikes(likeRepository.getLikesCount(item.getId()));
        dto.setDislikes(likeRepository.getDisLikesCount(item.getId()));
        dto.setLikeState(likeRepository.userLikeState(UUID.fromString(userId), item.getId()));
        return dto;
    }



}
