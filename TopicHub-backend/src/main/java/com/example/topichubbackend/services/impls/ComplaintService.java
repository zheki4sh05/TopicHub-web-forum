package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dao.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.entity.complaints.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.objectMapper.*;
import com.example.topichubbackend.mapper.objectMapper.impl.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.factories.*;

import java.util.*;
import java.util.stream.*;

public class ComplaintService implements IComplaintControl {
    private final static ComplaintService complaintService = new ComplaintService();
    private ComplaintService() { }
    public static ComplaintService  getInstance(){
        return complaintService;
    }

    private final ComplaintDao complaintDao  = DaoFactory.createComplaintDao();
    private final AuthDao authDao  =DaoFactory.createAuthDao();
    private final CommentDao commentDao= DaoFactory.createCommentDao();
    private final ArticleDao articleDao= DaoFactory.createArticleDao();
    private final IObjectMapper objectMapper  =new ObjectMapperImpl();

    private final String articleComplaint="article";
    private final String commentComplaint="comment";

    @Override
    public void create(String userId, ComplaintDto complaintDto) {

        User user = authDao.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        if(complaintDto.getType().equals(articleComplaint)){

            Optional<ArticleComplaint> complaint = complaintDao.findByArticleUserId(complaintDto.getTargetId(), userId);
            if(complaint.isEmpty()){

                createAndSaveArticleComplaint(complaintDto, user);

            }else{
                throw new EntityAlreadyExists();
            }

        }else if(complaintDto.getType().equals(commentComplaint)){
            Optional<CommentComplaint> complaint = complaintDao.findByCommentUserId(complaintDto.getTargetId(), userId);
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
    public List<ComplaintDto> findAllByType(String type) {

        if(type.equals(articleComplaint)){

            return complaintDao.findAllArticle().stream()
                    .map(objectMapper::mapFrom)
                    .collect(Collectors.toList());

        }else if(type.equals(commentComplaint)){
            return complaintDao.findAllComment().stream()
                    .map(objectMapper::mapFrom)
                    .collect(Collectors.toList());
        }else{
            throw  new BadRequestException();
        }

    }

    @Override
    public void deleteById(String complaintId, String type) {
        if(type.equals(articleComplaint)){

            var entity= complaintDao.findByIdArticle(complaintId).orElseThrow(EntityNotFoundException::new);
            complaintDao.delete(entity);

        }else if(type.equals(commentComplaint)){
            var entity= complaintDao.findByIdComment(complaintId).orElseThrow(EntityNotFoundException::new);
            complaintDao.delete(entity);
        }else{
            throw new BadRequestException();
        }

    }

    private void createAndSaveCommentComplaint(ComplaintDto complaintDto, User author) {

        Comment comment = commentDao.findByUuid(complaintDto.getTargetId())
                .orElseThrow(EntityNotFoundException::new);

        CommentComplaint complaint = CommentComplaint.builder()
                .id(UUID.randomUUID())
                .author(author)
                .title(complaintDto.getTitle())
                .body(complaintDto.getBody())
                .comment(comment)
                .build();

        complaintDao.save(complaint);

    }

    private void createAndSaveArticleComplaint(ComplaintDto complaintDto, User author) {

        Article article = articleDao.findById(Long.valueOf(complaintDto.getTargetId()))
                .orElseThrow(EntityNotFoundException::new);

        ArticleComplaint articleComplaint = ArticleComplaint.builder()
                .id(UUID.randomUUID())
                .author(author)
                .title(complaintDto.getTitle())
                .body(complaintDto.getBody())
                .article(article)
                .build();

        complaintDao.save(articleComplaint);


    }
}
