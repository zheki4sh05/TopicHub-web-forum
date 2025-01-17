package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dao.interfaces.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.mapper.objectMapper.*;
import com.example.topichubbackend.mapper.objectMapper.impl.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;
import jakarta.persistence.*;

import java.sql.*;
import java.time.*;
import java.util.*;

public class CommentsService implements ICommentsService {

    private final static CommentsService commentsService = new CommentsService();

    private CommentsService() { }
    public static CommentsService  getInstance(){
        return commentsService;
    }
    private final IEmailService emailService = ServiceFactory.getEmailService();
    private final ArticleRepository articleDao = RepositoryFactory.createArticleDao();
    private final AuthRepository authDao = RepositoryFactory.createAuthDao();
    private final CommentRepository commentDao = RepositoryFactory.createCommentDao();
    private final IObjectMapper objectMapper = new ObjectMapperImpl();
    @Override
    public List<CommentDto> fetch(String article) {
        List<Comment> comments = commentDao.findAllByArticleId(Long.valueOf(article));
        return mapToDtoList(comments,Long.valueOf(article));
    }

    @Override
    public CommentDto create(CommentDto commentDto, String userId) {
        Article article = articleDao.findById(commentDto.getArticleId()).orElseThrow(EntityNotFoundException::new);
        User author = authDao.findById(userId).orElseThrow(EntityExistsException::new);
        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .message(commentDto.getValue())
                .article(article)
                .author(author)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        if(commentDto.getParentId()!=null){
            comment.setParentComment(
                    commentDao.findByParentId(commentDto.getParentId()).orElseThrow(EntityNotFoundException::new)
            );
        }
        commentDao.save(comment);
        emailService.sendCommentNotification(comment, article.getAuthor());
        return objectMapper.mapFrom(comment, article.getId(), new HashSet<>());
    }

    @Override
    public CommentDto update(CommentDto commentDto, String userId) {
        Comment comment = commentDao.findByUuid(commentDto.getId()).orElseThrow(EntityNotFoundException::new);
        if(comment.getAuthor().getUuid().toString().equals(userId)){
            comment.setMessage(commentDto.getValue());
            commentDao.update(comment);
            return objectMapper.mapFrom(comment, commentDto.getArticleId(), new HashSet<>());
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void delete(String commentId, String userId) {
        Comment comment = commentDao.findByUuid(commentId).orElseThrow(EntityNotFoundException::new);
        if(comment.getAuthor().getUuid().toString().equals(userId)){

          commentDao.delete(comment);

        }else{
            throw new EntityNotFoundException();
        }
    }

    public List<CommentDto> mapToDtoList(List<Comment> comments,Long articleId) {
        Set<UUID> processedIds = new HashSet<>();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(objectMapper.mapFrom(comment,articleId, processedIds));
        }
        return commentDtos;
    }
}
