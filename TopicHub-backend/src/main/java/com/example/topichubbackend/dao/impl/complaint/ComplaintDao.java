package com.example.topichubbackend.dao.impl.complaint;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.entity.complaints.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.validation.constraints.*;

import java.util.*;

public class ComplaintDao implements ComplaintRepository {

    private final CommentComplaintDao complaintDao = RepositoryFactory.createCommentComplaintDao();
    private final ArticleComplaintDao articleComplaintDao = RepositoryFactory.createArticleComplaintDao();

    public Optional<ArticleComplaint> findByArticleUserId(@NotEmpty String targetId, String userId) {
      return articleComplaintDao.findByArticleUserId( targetId,  userId);
    }

    public Optional<CommentComplaint> findByCommentUserId(String targetId, String userId) {
        return complaintDao.findByCommentUserId(targetId, userId);
    }

    public List<ArticleComplaint> findAllArticle() {
      return articleComplaintDao.findAllArticle();
    }

    public List<CommentComplaint> findAllComment() {
       return complaintDao.findAllComment();
    }

    public Optional<ArticleComplaint> findByIdArticle(String complaintId) {
       return articleComplaintDao.findByIdArticle(complaintId);
    }
    public Optional<CommentComplaint> findByIdComment(String complaintId) {
       return complaintDao.findByIdComment(complaintId);
    }

    @Override
    public void delete(ArticleComplaint entity) {
        articleComplaintDao.delete(entity);
    }
    @Override
    public void delete(CommentComplaint entity) {
        complaintDao.delete(entity);
    }

    @Override
    public void save(CommentComplaint complaint) {
        complaintDao.save(complaint);
    }

    @Override
    public void save(ArticleComplaint articleComplaint) {
        articleComplaintDao.save(articleComplaint);
    }
}
