package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

public interface IReactionService {
    ReactionDto check(String articleId, String authorId, String userId);

    void makeReaction(String type, String value, String email, String targetId);

    void manageSubscription(Integer value, String authorEmail, String email);

    void manageBookmarks(Integer value, String article, String userId);
}
