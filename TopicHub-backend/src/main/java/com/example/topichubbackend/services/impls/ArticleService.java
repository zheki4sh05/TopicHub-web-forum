package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import com.example.topichubbackend.util.objectMapper.*;
import com.example.topichubbackend.util.objectMapper.impl.*;

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

    private IObjectMapper objectMapper = new ObjectMapperImpl();

    public static final String dilimiter = "|";
    @Override
    public void create(ArticleDto articleDto) {

        List<Hub> hubList = hubDao.fetchAll();

        final Article article = Article.builder()
                .theme(articleDto.getTheme())
                .keyWords(String.join(dilimiter, articleDto.getKeyWords()))
                .likes(0L)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .dislikes(0l)
                .hub(hubList.stream().filter(item->item.getId().equals(articleDto.getHub())).findFirst().orElseThrow())
                .build();

       articleDao.save(article);
       articleDao.merge(article);

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
    public ArticleBatchDto fetch(Integer param, Integer page) {

        ArticleBatchDto articleBatchDto = new ArticleBatchDto();

        List<Hub> hubList = hubDao.fetchAll();

        Optional<Hub> result = hubList.stream().filter(item->item.getId().equals(param)).findFirst();

        result.ifPresentOrElse(
                (item)->{
                    Long totalCount = articleDao.calcTotalEntitiesCount();
                    Integer pageCount = articleDao.getLastPageNumber(totalCount);

                    articleBatchDto.setPageCount(pageCount);
                    articleBatchDto.setArticleDtoList(getArticleByType(result.get().getId(),page));

                    },
                ()->{ throw new EntityNotFoundException("No hub with such name!");
                });

        return articleBatchDto;
    }

    private List<ArticleDto> getArticleByType(Integer id, Integer page) {
        List<Article> articles;
        if(id.equals(0)){
            articles = articleDao.getSortedAndPaginated(page);
        }else{
            articles = articleDao.getSortedAndPaginated(page,id);
        }

        List<ArticleDto> articleDtos = new ArrayList<>();

        articles.forEach(item->{

            ArticleDto articleDto = objectMapper.mapFrom(item, dilimiter);

            articleDto.setList(getArticlePart(articleDto.getId()).stream().map(part->objectMapper.mapFrom(part)).collect(Collectors.toList()));

            articleDtos.add(articleDto);
        });


        return  articleDtos;

    }

    private List<ArticlePart> getArticlePart(Long id){
        return  articleDao.findByArticleId(id);
    }


}
