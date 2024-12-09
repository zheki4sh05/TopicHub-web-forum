package com.example.topichubbackend.services.interfaces;

import com.example.topichubbackend.dto.*;

public interface IReactionService {
    ReactionDto check(String articleId, String authorId, String userId);
}
