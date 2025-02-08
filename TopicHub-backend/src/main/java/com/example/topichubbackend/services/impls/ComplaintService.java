package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.model.complaints.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.repository.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import java.util.*;
import java.util.stream.*;

@Service
@AllArgsConstructor
public class ComplaintService implements IComplaintControl {
    private final ArticleComplaintRepository articleComplaintRepository;
    private final CommentComplaintRepository commentComplaintRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepo articleRepository;
    private final ComplaintMapper complaintMapper;

    private final String ARTICLE="article";
    private final String COMMENT="comment";

    @Override
    public void create(String userId, ComplaintDto complaintDto) {

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(EntityNotFoundException::new);

        if(complaintDto.getType().equals(ARTICLE)){

            Optional<ArticleComplaint> complaint = articleComplaintRepository.findByArticleUserId(Long.valueOf(complaintDto.getTargetId()), UUID.fromString(userId));
            if(complaint.isEmpty()){

                createAndSaveArticleComplaint(complaintDto, user);

            }else{
                throw new EntityAlreadyExists();
            }

        }else if(complaintDto.getType().equals(COMMENT)){
            Optional<CommentComplaint> complaint = commentComplaintRepository.findByCommentUserId(complaintDto.getTargetId(), userId);
            if(complaint.isEmpty()){
                createAndSaveCommentComplaint(complaintDto, user);
            }else{
                throw new EntityAlreadyExists();
            }
        }else{
            throw new BadRequestException();
        }

    }

    @Override
    public ComplaintDto findById(String id, String type) {
        if(type.equals(ARTICLE)){
          ArticleComplaint articleComplaint =   articleComplaintRepository.findById(UUID.fromString(id))
                  .orElseThrow(EntityNotFoundException::new);
            return complaintMapper.mapFrom(articleComplaint);

        }else if(type.equals(COMMENT)){
            CommentComplaint commentComplaint =   commentComplaintRepository.findById(UUID.fromString(id))
                    .orElseThrow(EntityNotFoundException::new);
            return complaintMapper.mapFrom(commentComplaint);
        }else{
            throw  new BadRequestException();
        }
    }

    @Override
    public PageResponse<ComplaintDto> findAllByType(String type, Pageable pageable) {

        if(type.equals(ARTICLE)){
            var result = articleComplaintRepository.findAll(pageable);
            return PageResponse.map(complaintMapper::mapFrom, result);

        }else if(type.equals(COMMENT)){
            var result = commentComplaintRepository.findAllComment(pageable);
            return PageResponse.map(complaintMapper::mapFrom, result);
        }else{
            throw  new BadRequestException();
        }

    }

    @Override
    public void deleteById(String complaintId, String type) {
        if(type.equals(ARTICLE)){
            ArticleComplaint entity= articleComplaintRepository.findByIdArticle(complaintId).orElseThrow(EntityNotFoundException::new);
            articleComplaintRepository.delete(entity);
        }else if(type.equals(COMMENT)){
            CommentComplaint entity= commentComplaintRepository.findByIdComment(complaintId).orElseThrow(EntityNotFoundException::new);
            commentComplaintRepository.delete(entity);
        }else{
            throw new BadRequestException();
        }

    }

    private void createAndSaveCommentComplaint(ComplaintDto complaintDto, User author) {
        Comment comment = commentRepository.findById(UUID.fromString(complaintDto.getTargetId()))
                .orElseThrow(EntityNotFoundException::new);
        CommentComplaint complaint = CommentComplaint.builder()
                .id(UUID.randomUUID())
                .author(author)
                .title(complaintDto.getTitle())
                .body(complaintDto.getBody())
                .comment(comment)
                .build();
        commentComplaintRepository.save(complaint);
    }

    private void createAndSaveArticleComplaint(ComplaintDto complaintDto, User author) {

        var article = articleRepository.findById(Long.valueOf(complaintDto.getTargetId()))
                .orElseThrow(EntityNotFoundException::new);

        ArticleComplaint articleComplaint = ArticleComplaint.builder()
                .id(UUID.randomUUID())
                .author(author)
                .title(complaintDto.getTitle())
                .body(complaintDto.getBody())
                .article(article)
                .build();

        articleComplaintRepository.save(articleComplaint);
    }
}
