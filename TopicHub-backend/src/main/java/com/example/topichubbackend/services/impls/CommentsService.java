package com.example.topichubbackend.services.impls;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@Service
@AllArgsConstructor
public class CommentsService implements ICommentsService {
    private final IEmailService emailService;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    //private final CommentMapper commentMapper;
    @Override
    public List<CommentDto> fetch(String article) {
        List<Comment> comments = commentRepository.findAllByArticleId(Long.valueOf(article));
        return mapToDtoList(comments,Long.valueOf(article));
    }

    @Override
    public CommentDto create(CommentDto commentDto, String userId) {
        Article article = articleRepository.findById(commentDto.getArticleId()).orElseThrow(EntityNotFoundException::new);
        User author = userRepository.findById(UUID.fromString(userId)).orElseThrow(EntityExistsException::new);
        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .message(commentDto.getValue())
                .article(article)
                .author(author)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        if(commentDto.getParentId()!=null){
            comment.setParentComment(
                    commentRepository.findById(commentDto.getParentId()).orElseThrow(EntityNotFoundException::new)
            );
        }
        commentRepository.save(comment);
        emailService.sendCommentNotification(comment, article.getAuthor());
       // return commentMapper.mapFrom(comment, article.getId(), new HashSet<>());
        return new CommentDto();
    }

    @Override
    public CommentDto update(CommentDto commentDto, String userId) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(EntityNotFoundException::new);
        if(comment.getAuthor().getUuid().toString().equals(userId)){
            comment.setMessage(commentDto.getValue());
            commentRepository.save(comment);
//            return commentMapper.mapFrom(comment, commentDto.getArticleId(), new HashSet<>());
            return new CommentDto();
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void delete(String commentId, String userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        if(comment.getAuthor().getUuid().toString().equals(userId)){
            commentRepository.delete(comment);
        }else{
            throw new EntityNotFoundException();
        }
    }

    public List<CommentDto> mapToDtoList(List<Comment> comments,Long articleId) {
        Set<UUID> processedIds = new HashSet<>();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
//            commentDtos.add(commentMapper.mapFrom(comment,articleId, processedIds));
            commentDtos.add( new CommentDto());
        }
        return commentDtos;
    }
}
