package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

import java.util.*;
import java.util.stream.*;

public class ReactionService implements IReactionService {
    private final static ReactionService reactionService = new ReactionService();
    private ReactionService() { }
    public static ReactionService  getInstance(){
        return reactionService;
    }

    private final ReactionRepository reactionDao = RepositoryFactory.createReactionDao();

    private final AuthRepository authDao = RepositoryFactory.createAuthDao();
    private final ArticleRepository articleDao = RepositoryFactory.createArticleDao();
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

        switch (type) {
            case "article" -> {
                Article article = articleDao.findById(targetId).orElseThrow(EntityNotFoundException::new);
                User user = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
                Optional<Likes> reaction = reactionDao.findById(article.getId(), user.getUuid());
                reaction.ifPresentOrElse(
                        (item) -> updateReaction(item, value),
                        () -> createNewReaction(article, user, value)
                );
            }
            case "comment" -> {
                reactionDao.reactionComment(value, userId, targetId);
            }
            default -> {
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
           User user  = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
           User author = authDao.findById(authorId).orElseThrow(EntityNotFoundException::new);
           reactionDao.subscribe(author,user);
       }else{
           reactionDao.unsubscribe(authorId,userId);
       }

    }

    @Override
    public void manageBookmarks(Integer value, String articleId, String userId) {
        if(value==1){
            User user  = authDao.findById(userId).orElseThrow(EntityNotFoundException::new);
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

    @Override
    public List<AuthorDto> fetchAllSubscribes(String id) {

        List<Subscription> subscriptions = reactionDao.findSubscribesById(id);

        return subscriptions.stream().map(item->AuthorDto.builder()
                .login(item.getAuthor().getLogin())
                .email(item.getAuthor().getEmail())
                .id(item.getAuthor().getUuid().toString())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> fetchAllFollowers(String id) {
        List<Subscription> userList = reactionDao.findFollowersById(id);
        return userList.stream().map(item->AuthorDto.builder()
                .login(item.getAuthor().getLogin())
                .email(item.getAuthor().getEmail())
                .id(item.getAuthor().getUuid().toString())
                .build()).collect(Collectors.toList());
    }
}
