package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import com.example.topichubbackend.mapper.objectMapper.*;
import com.example.topichubbackend.mapper.objectMapper.impl.*;

import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;


public class ArticleService implements IArticleService {
    private final static ArticleService articleService = new ArticleService();
    private ArticleService() { }
    public static ArticleService  getInstance(){
        return articleService;
    }
    private final ArticleDao articleDao = DaoFactory.createArticleDao();
    private final HubDao hubDao = DaoFactory.createHubDao();

    private final AuthDao authDao = DaoFactory.createAuthDao();

    private final ReactionDao reactionDao = DaoFactory.createReactionDao();

    private final IObjectMapper objectMapper = new ObjectMapperImpl();

    private final CommentDao commentDao = DaoFactory.createCommentDao();

    public static final String dilimiter = "|";
    @Override
    public void create(ArticleDto articleDto, String id) {

        List<Hub> hubList = hubDao.fetchAll();

        User user = authDao.findById(id).orElseThrow(EntityNotFoundException::new);

        final Article article = Article.builder()
                .theme(articleDto.getTheme())
                .keyWords(String.join(dilimiter, articleDto.getKeyWords()))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .author(user)
                .hub(hubList.stream().filter(item->item.getId().equals(articleDto.getHub())).findFirst().orElseThrow())
                .build();

       articleDao.save(article);

        articleDto.getList().forEach(item->{

            articleDao.save(ArticlePart.builder()
                    .uuid(UUID.randomUUID())
                    .id(item.getId())
                    .value(item.getValue())
                    .name(item.getName())
                    .type(item.getType())
                    .article(article)
                    .build());
        });

    }

    @Override
    public ArticleBatchDto fetch(Integer param, Integer page, String userId) {
        switch (param){
            case 0:{
                return fetchFeed(page,userId);
            } case -1:{
                return fetchFromUserSubscribes(page, userId);
            } default:{
                return fetchFromHubs(param,page, userId);
            }
        }
    }

    private ArticleBatchDto fetchFromHubs(Integer param, Integer page, String userId) {

        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        List<Hub> hubList = hubDao.fetchAll();
        Optional<Hub> result = hubList.stream().filter(item->item.getId().equals(param)).findFirst();
        result.ifPresentOrElse(
                (item)->{
                    Long totalCount = articleDao.calcTotalEntitiesCountByHub(param);
                    Integer pageCount = articleDao.getLastPageNumber(totalCount);
                    articleBatchDto.setPageCount(pageCount);
                    articleBatchDto.setArticleDtoList(getArticleByType(result.get().getId(),page,userId));

                },
                ()->{ throw new EntityNotFoundException("No hub with such name!");
                });

        return articleBatchDto;

    }

    private ArticleBatchDto fetchFromUserSubscribes(Integer page, String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        if(userId==null){
            articleBatchDto.setPageCount(0);
            articleBatchDto.setArticleDtoList(new ArrayList<>());
            return articleBatchDto;
        }else{
            Long totalCount = articleDao.calcTotalSubscribeEntitiesCount(userId);
            Integer pageCount = articleDao.getLastPageNumber(totalCount);
            articleBatchDto.setPageCount(pageCount);
            articleBatchDto.setArticleDtoList(getSubscribesArticles(page,userId));
            return articleBatchDto;
        }

    }

    private List<ArticleDto> getSubscribesArticles(Integer page, String userId) {

        List<Article> articles = articleDao.getSubscribeArticles(page,userId);
        return doMapping(articles,userId);

    }

    private ArticleBatchDto fetchFeed(Integer page, String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleDao.calcTotalEntitiesCount();
        Integer pageCount = articleDao.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        articleBatchDto.setArticleDtoList(getFeed(page,userId));
        return articleBatchDto;
    }

    private List<ArticleDto> getFeed(Integer page, String userId) {
       List<Article> articles = articleDao.getSortedAndPaginated(page);
        return doMapping(articles,userId);
    }

    @Override
    public ArticleBatchDto fetch(Integer page,String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleDao.calcTotalUserArticles(userId);
        Integer pageCount = articleDao.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        articleBatchDto.setArticleDtoList(getOwnArticles(page, userId));
        return articleBatchDto;
    }

    @Override
    public void delete(String id, String userId) {
        Article article = articleDao.findById(Long.parseLong(id)).orElseThrow(EntityNotFoundException::new);
        if(article.getAuthor().getUuid().toString().equals(userId)){
            articleDao.delete(article);
        }else{
            throw new EntityNotFoundException("Not found article for user");
        }
    }

    @Override
    public ArticleBatchDto search(String author, String theme, String keywords, String userId) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        List<Article> articles = new ArrayList<>();

        if(!author.isEmpty() && theme.isEmpty() && keywords.isEmpty() ){

            articles.addAll(articleDao.search(author));
            articleBatchDto.setArticleDtoList(doMapping(articles,userId));
        }else if(!author.isEmpty()){
            articles.addAll(articleDao.search(theme,  keywords));
            var filtered = articles.stream()
                    .filter(item->item.getAuthor().getLogin()
                            .startsWith(author));

            articleBatchDto.setArticleDtoList(doMapping(filtered.collect(Collectors.toList()),userId));
        }else{
            articles.addAll(articleDao.search(theme,  keywords));
            articleBatchDto.setArticleDtoList(doMapping(articles,userId));

        }
        return articleBatchDto;
    }

    @Override
    public ArticleBatchDto fetchBookMarks(String userId, Integer page) {
        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleDao.calcTotalBookmarksCount(userId);
        Integer pageCount = articleDao.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        articleBatchDto.setArticleDtoList(getBookmarks(page, userId));
        return articleBatchDto;
    }

    @Override
    public ArticleBatchDto fetch(Integer page, String userId, String otherUserId) {

        ArticleBatchDto articleBatchDto = new ArticleBatchDto();
        Long totalCount = articleDao.calcTotalUserArticles(otherUserId);
        Integer pageCount = articleDao.getLastPageNumber(totalCount);
        articleBatchDto.setPageCount(pageCount);
        articleBatchDto.setArticleDtoList(gteOtherArticles(page, userId,otherUserId));
        return articleBatchDto;
    }

    @Override
    public void deleteAdmin(String targetId) {
        Article article = articleDao.findById(Long.parseLong(targetId)).orElseThrow(EntityNotFoundException::new);
        articleDao.delete(article);
    }

    private List<ArticleDto> gteOtherArticles(Integer page, String userId, String otherUserId) {
        List<Article> articles = articleDao.getSortedAndPaginated(ArticleDao.authorArticles, page,otherUserId );
        return doMapping(articles,userId);

    }

    private List<ArticleDto> getBookmarks(Integer page, String userId) {

        List<Article> articles = articleDao.getSortedAndPaginated(ArticleDao.bookmarks, page,userId );
        return doMapping(articles,userId);

    }


    private List<ArticleDto> getArticleByType(Integer id, Integer page, String userId) {
        List<Article> articles = articleDao.getSortedAndPaginated(page,id);
        return  doMapping(articles,userId);
    }

    private List<ArticleDto> getOwnArticles(Integer page, String userId){
        List<Article> articles = articleDao.getSortedAndPaginated(ArticleDao.authorArticles, page,userId );
        return doMapping(articles,userId);
    }

    private List<ArticlePart> getArticlePart(Long id){
        return  articleDao.findByArticleId(id);
    }

    private List<ArticleDto> doMapping(List<Article> articles,String userId){
        List<ArticleDto> articleDtos = new ArrayList<>();
        articles.forEach(item->{
            item.setArticlePartList(getArticlePart(item.getId()));
            ArticleDto articleDto = getLikesAndDislikes(item, userId);
            articleDto.setCommentsCount(getCommentsCount(item.getId()));
            articleDtos.add(articleDto);
        });
        return articleDtos;
    }

    private Long getCommentsCount(Long id) {
        return commentDao.calcArticleCommentsCount(id);
    }

    private ArticleDto getLikesAndDislikes(Article item, String userId){
        var dto = objectMapper.mapFrom(item, dilimiter);
        Long[] list= reactionDao.getLikesAndDislikes(item.getId());
        dto.setLikes(list[0]);
        dto.setDislikes(list[1]);
        dto.setLikeState(reactionDao.userLikeState(userId, item.getId()));
        return dto;
    }



}
