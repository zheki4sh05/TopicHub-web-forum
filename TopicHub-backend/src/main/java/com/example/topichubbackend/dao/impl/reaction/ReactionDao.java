package com.example.topichubbackend.dao.impl.reaction;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.persistence.*;
import org.hibernate.exception.*;

import java.util.*;

public class ReactionDao implements ReactionRepository {
    private final LikeDao likeDao = RepositoryFactory.createLikeDao();
    private final SubscriptionDao subscriptionDao = RepositoryFactory.createSubscriptionDao();
    private final BookmarkDao bookmarkDao = RepositoryFactory.createBookMarkDao();

    public Boolean checkSubscribe(String userId, String authorId) {
       return subscriptionDao.checkSubscribe(userId, authorId);
    }

    @Override
    public Boolean checkMarked(String userId, String articleId) {
        return bookmarkDao.checkMarked(userId,articleId);
    }

    public void subscribe(User authorId, User userId) {
          subscriptionDao.subscribe(authorId, userId);
    }

    public void unsubscribe(String author, String user) {
       subscriptionDao.unsubscribe(author, user);
    }

    public void addBookmark(Article article, User user) {
        bookmarkDao.addBookmark(article,user);
    }

    public void removeBookmark(String articleId, String userId) {
        bookmarkDao.removeBookmark(articleId,userId);
    }

    public void reactionComment(Integer value, String userId, Long targetId) {
        throw new UnsupportedException();
    }

    public Optional<Likes> findById(Long targetId, UUID uuid) {
       return likeDao.findById(targetId, uuid);
    }

    public void updateReaction(Likes item) {
        likeDao.updateReaction(item);
    }

    public void saveReaction(Likes build) {
       likeDao.saveReaction(build);
    }

    public void removeArticleReaction(String userId, Long articleId) {
        likeDao.removeArticleReaction(userId, articleId);
    }

    @Override
    public List<Subscription> findSubscribesById(String id) {
        return subscriptionDao.findSubscribesById(id);
    }

    @Override
    public List<Subscription> findFollowersById(String id) {
        return subscriptionDao.findFollowersById(id);
    }

    public Long[] getLikesAndDislikes(Long articleId) {
       return likeDao.getLikesAndDislikes(articleId);
    }

    public Integer userLikeState(String userId, Long id) {
        return likeDao.userLikeState(userId,id);
    }
}
