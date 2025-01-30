package com.example.topichubbackend.repository;

import com.example.topichubbackend.model.complaints.*;

import java.util.*;

public interface ComplaintRepository {
    Optional<ArticleComplaint> findByArticleUserId(String targetId, String userId);

    Optional<CommentComplaint> findByCommentUserId(String targetId, String userId);

    List<ArticleComplaint> findAllArticle();

    List<CommentComplaint> findAllComment();

    Optional<ArticleComplaint> findByIdArticle(String complaintId);
    Optional<CommentComplaint> findByIdComment(String complaintId);

     void delete(ArticleComplaint entity);
    void delete(CommentComplaint entity);

    void save(CommentComplaint complaint);

    void save(ArticleComplaint articleComplaint);
}
