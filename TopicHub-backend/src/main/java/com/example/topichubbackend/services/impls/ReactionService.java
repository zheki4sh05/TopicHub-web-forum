package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

import java.util.*;

public class ReactionService implements IReactionService {
    private final static ReactionService reactionService = new ReactionService();
    private ReactionService() { }
    public static ReactionService  getInstance(){
        return reactionService;
    }

    private final ReactionDao reactionDao = DaoFactory.createReactionDao();

    private final AuthDao authDao = DaoFactory.createAuthDao();
    private final ArticleDao articleDao = DaoFactory.createArticleDao();
    @Override
    public ReactionDto check(String articleId, String authorId, String userId) {

        Boolean subscribe =reactionDao.checkSubscribe(userId, authorId);

        Boolean marked = reactionDao.checkMarked(userId, articleId);

        return ReactionDto.builder()
                .isMarked(marked)
                .isSubscribe(subscribe)
                .build();
    }

    @Override
    public void makeReaction(String type, Integer value, String userId, Long targetId) {

        switch(type){
            case "article":{
                Article article = articleDao.findById(targetId).orElseThrow(EntityNotFoundException::new);
                User user = authDao.findById(userId);
                Optional<Likes> reaction = reactionDao.findById(article.getId(), user.getUuid());
                reaction.ifPresentOrElse(
                        (item)-> updateReaction(item, value),
                        ()-> createNewReaction(article, user, value)
                );
                break;
            }
            case "comment":{
                reactionDao.reactionComment(value,userId,targetId);
                break;
            }
            default:{
                throw new BadRequestException();
            }
        }
    }

    private void updateReaction(Likes item, Integer value) {

        item.setState(value);
        reactionDao.updateReaction(item);

    }

    private void createNewReaction(Article article, User user, Integer value) {


        reactionDao.saveReaction(Likes.builder()
                .uuid(UUID.randomUUID())
                .state(value)
                .article(article)
                .user(user)
                .build());

    }

    @Override
    public void manageSubscription(Integer value, String authorId, String userId) {

       if(value==1){
           User user  = authDao.findById(userId);
           User author = authDao.findById(authorId);
           reactionDao.subscribe(author,user);
       }else{
           reactionDao.unsubscribe(authorId,userId);
       }

    }

    @Override
    public void manageBookmarks(Integer value, String articleId, String userId) {
        if(value==1){
            User user  = authDao.findById(userId);
            Article article = articleDao.findById(Long.valueOf(articleId)).orElseThrow(EntityNotFoundException::new);
            reactionDao.addBookmark(article,user);
        }else{
            reactionDao.removeBookmark(articleId,userId);
        }
    }

    @Override
    public void removeReaction(String type, String userId, Long articleId) {
        switch(type){
            case "article":{

                reactionDao.removeArticleReaction(userId, articleId);
                break;
            }
            case "comment":{
                break;
            }
            default:{
                throw new BadRequestException();
            }
        }
    }
}
