package com.example.topichubbackend.dao.interfaces;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.entity.complaints.*;

import java.util.*;

public interface CommentRepository {
    Long calcArticleCommentsCount(Long id);
    Optional<Comment> findByUuid(String targetId);


    List<Comment> findAllByArticleId(Long aLong);

    Optional<Comment> findByParentId(String parentId);

    Comment save(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);
}
