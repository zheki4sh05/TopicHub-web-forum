package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

public class ReactionService implements IReactionService {
    private final static ReactionService reactionService = new ReactionService();
    private ReactionService() { }
    public static ReactionService  getInstance(){
        return reactionService;
    }

    private final ReactionDao reactionDao = DaoFactory.createReactionDao();
    @Override
    public ReactionDto check(String articleId, String authorId, String userId) {

        Boolean subscribe =reactionDao.checkSubscribe(userId, authorId);

        Boolean marked = reactionDao.checkMarked(userId, articleId);

        return ReactionDto.builder()
                .isMarked(marked)
                .isSubscribe(subscribe)
                .build();
    }
}
