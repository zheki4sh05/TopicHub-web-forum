package com.example.topichubbackend.dao.interfaces;

import com.example.topichubbackend.entity.*;

import java.util.*;

public interface ReactionRepository {
    Long[] getLikesAndDislikes(Long id);

    Integer userLikeState(String userId, Long id);

    Boolean checkSubscribe(String userId, String authorId);

    Boolean checkMarked(String userId, String articleId);

    Optional<Likes> findById(Long id, UUID uuid);

    void reactionComment(Integer value, String userId, Long targetId);

    void updateReaction(Likes item);

    void saveReaction(Likes build);

    void subscribe(User author, User user);

    void unsubscribe(String authorId, String userId);

    void addBookmark(Article article, User user);

    void removeBookmark(String articleId, String userId);

    void removeArticleReaction(String userId, Long articleId);
    List<Subscription> findSubscribesById(String id);

    List<Subscription> findFollowersById(String id);
}
