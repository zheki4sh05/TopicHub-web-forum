package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.filters.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.persistence.criteria.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@AllArgsConstructor
@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ArticlePartRepository articlePartRepository;
    private final HubRepository hubDao;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ArticleMapper articleMapper;
    private final ArticleRepo articleRepo;
    private final ArticleViewRepository articleViewRepository;
    private final FilterQueryFactory filterQueryFactory;

    @Override
    @Transactional
    public void create(ArticleDto articleDto, String id) {
        final String DILIMITER = "|";
        List<Hub> hubList = hubDao.findAll();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
        final var article = ArticleEntity.builder()
                .theme(articleDto.getTheme())
                .keyWords(String.join(DILIMITER, articleDto.getKeyWords()))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .author(user)
                .status(StatusDto.MODERATION.type())
                .hub(hubList.stream().filter(item->item.getId().equals(articleDto.getHub())).findFirst().orElseThrow())
                .build();

        articleRepo.save(article);

        articleDto.getList().forEach(item->{
            articlePartRepository.save(ArticlePart.builder()
                    .uuid(UUID.randomUUID())
                    .id(item.getId())
                    .value(item.getValue())
                    .name(item.getName())
                    .type(item.getType())
                    .created(item.getCreated())
                    .articleEntity(article)
                    .build());
        });


    }

    @Override
    public void delete(String id, String userId) {
        var article = articleRepo.findById(Long.parseLong(id)).orElseThrow(EntityNotFoundException::new);
        if(article.getAuthor().getUuid().toString().equals(userId)){
            articleRepo.delete(article);
        }else{
            throw new EntityNotFoundException("Not found article for user");
        }
    }

    @Override
    @Transactional
    public PageResponse<ArticleDto> search(SearchDto searchDto) {
        Pageable pageable= PageRequest.of(searchDto.getArticleFilterDto().getPage()-1,15);
        Page<Article> articles = articleViewRepository.searchBy(searchDto.getAuthor(),
                searchDto.getTheme(),
                searchDto.getKeywords(),
                 pageable);
        PageResponse<Article> pageResponse = PageResponse.map(articles);
        checkLike(searchDto.getUserId(), pageResponse);
        return PageResponse.map(articleMapper::toDto, articles);

    }
    @Override
    @Transactional
    public PageResponse<ArticleDto> fetchBookMarks(String userId, Integer page) {
        Pageable pageable= PageRequest.of(page-1,15);
        Page<Article> articles = articleViewRepository.findBookmarks(UUID.fromString(userId),pageable);
        PageResponse<Article> pageResponse = PageResponse.map(articles);
        checkLike(userId, pageResponse);
        return PageResponse.map(articleMapper::toDto, articles);
    }



    @Override
    @Transactional
    public PageResponse<ArticleDto> fetch(ArticleFilterDto articleFilter) {
        CriteriaQuery<Article> articleCriteriaQuery = filterQueryFactory.createQuery(articleFilter);
        PageResponse<Article> articlePage = articleRepository.findByQuery(
                articleCriteriaQuery,
                PageRequest.of(articleFilter.getPage(),15));
        checkLike(articleFilter.getUserId(), articlePage);
        return PageResponse.map(articleMapper::toDto, articlePage);
    }
    @Override
    public void deleteAdmin(String targetId) {
        var article = articleRepo.findById(Long.parseLong(targetId)).orElseThrow(EntityNotFoundException::new);
        articleRepo.delete(article);
    }

    @Override
    public void update(ArticleDto updatedArticle, String id) {
        var delArticle = articleRepo.findById(updatedArticle.getId()).orElseThrow(()->new EntityNotFoundException("Статья не найдена"));
        articleRepo.delete(delArticle);
        create(updatedArticle, id);
    }


    @Override
    @Transactional
    public void update(ArticleStatusDto articleStatusDto) {
            var article = articleRepo.findById(Long.valueOf(articleStatusDto.getId()))
                    .orElseThrow(EntityNotFoundException::new);
            article.setStatus(articleStatusDto.getStatus());
            articleRepo.save(article);
    }

    @Override
    @Transactional
    public ArticleDto findById(String id) {
        var a = articleViewRepository.findById(Long.parseLong(id));
        return articleMapper.toDto(a
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public String getStatusNameById(String id) {
        return articleViewRepository.getStatus(id);
    }

    private void checkLike(String userId, PageResponse<Article> pageResponse){
        if(userId!=null){
            pageResponse.getItems().forEach(item->item.setState(
                    likeRepository.userLikeState(
                            UUID.fromString(userId),
                            item.getId()
                    )));
        }
    }



}
