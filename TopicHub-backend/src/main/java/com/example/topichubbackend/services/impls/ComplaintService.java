package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.function.*;

@Service
@AllArgsConstructor
public class ComplaintService implements IComplaintControl {

    private final ICommentComplaintService commentComplaintService;
    private final IArticleComplaintService articleComplaintService;

    @Override
    public void create(ComplaintDto complaintDto) {
        createCommand(complaintDto.getType(),
                articleComplaintService::create,
                commentComplaintService::create
                ,complaintDto);

    }

    @Override
    public ComplaintDto findById(String id, String type) {
       return createCommand(type,
                articleComplaintService::findById,
                commentComplaintService::findById,
                id
                );

//        if(name.equals(ARTICLE)){
//          ArticleComplaint articleComplaint =   articleComplaintRepository.findById(UUID.fromString(id))
//                  .orElseThrow(EntityNotFoundException::new);
//            return complaintMapper.mapFrom(articleComplaint);
//
//        }else if(name.equals(COMMENT)){
//            CommentComplaint commentComplaint =   commentComplaintRepository.findById(UUID.fromString(id))
//                    .orElseThrow(EntityNotFoundException::new);
//            return complaintMapper.mapFrom(commentComplaint);
//        }else{
//            throw  new BadRequestException();
//        }
    }

    @Override
    public PageResponse<ComplaintDto> findAllByType(String type, Pageable pageable) {
        return  createCommand(type,
                articleComplaintService::findAll,
                commentComplaintService::findAll,
                pageable
        );
//        if(name.equals(ARTICLE)){
//            var result = articleComplaintRepository.findAll(pageable);
//            return PageResponse.map(complaintMapper::mapFrom, result);
//        }else if(name.equals(COMMENT)){
//            var result = commentComplaintRepository.findAllComment(pageable);
//            return PageResponse.map(complaintMapper::mapFrom, result);
//        }else{
//            throw  new BadRequestException();
//        }

    }

    @Override
    public void deleteById(String complaintId, String type) {
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setId(complaintId);
          createCommand(type,
                articleComplaintService::deleteById,
                commentComplaintService::deleteById,
                  complaintId);
//        if(name.equals(ARTICLE)){
//            ArticleComplaint entity= articleComplaintRepository.findByIdArticle(UUID.fromString(complaintId)).orElseThrow(EntityNotFoundException::new);
//            articleComplaintRepository.delete(entity);
//        }else if(name.equals(COMMENT)){
//            CommentComplaint entity= commentComplaintRepository.findByIdComment(complaintId).orElseThrow(EntityNotFoundException::new);
//            commentComplaintRepository.delete(entity);
//        }else{
//            throw new BadRequestException();
//        }

    }

//    private void createAndSaveCommentComplaint(ComplaintDto complaintDto, User author) {
//        Comment comment = commentRepository.findById(UUID.fromString(complaintDto.getTargetId()))
//                .orElseThrow(EntityNotFoundException::new);
//        CommentComplaint complaint = CommentComplaint.builder()
//                .id(UUID.randomUUID())
//                .author(author)
//                .title(complaintDto.getTitle())
//                .body(complaintDto.getBody())
//                .comment(comment)
//                .build();
//        commentComplaintRepository.save(complaint);
//    }
//
//    private void createAndSaveArticleComplaint(ComplaintDto complaintDto, User author) {
//
//        var article = articleRepository.findById(Long.valueOf(complaintDto.getTargetId()))
//                .orElseThrow(EntityNotFoundException::new);
//
//        ArticleComplaint articleComplaint = ArticleComplaint.builder()
//                .id(UUID.randomUUID())
//                .author(author)
//                .title(complaintDto.getTitle())
//                .body(complaintDto.getBody())
//                .article(article)
//                .date(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//
//        articleComplaintRepository.save(articleComplaint);
//    }

    private <T,R> T createCommand(String type, Function<R,T> article, Function<R,T> comment, R dto){
        final String ARTICLE = "article";
        final String COMMENT = "comment";
        switch (type){
            case ARTICLE ->{
                return article.apply(dto);
            }
            case COMMENT ->{
                 return comment.apply(dto);
            }
            default -> throw new BadRequestException();
        }
    }
}
