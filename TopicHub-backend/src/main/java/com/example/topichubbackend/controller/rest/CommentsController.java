package com.example.topichubbackend.controller.rest;
import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
public class CommentsController {

    private final ICommentsService commentsService;
    private final CustomSecurityExpression customSecurityExpression;


    @PostMapping("/")
    public void doPost(@RequestBody CommentDto commentDto) {
        String userId =customSecurityExpression.getUserId();
        log.info("new comment: {}", commentDto);
        CommentDto created = commentsService.create(commentDto, userId);
        log.info("created comment: {}", created);
    }

    @PutMapping("/")
    public void doPut(@RequestBody CommentDto commentDto){
        String userId = customSecurityExpression.getUserId();
        log.info("new comment: {}", commentDto);
        CommentDto created = commentsService.update(commentDto, userId);
        log.info("updated comment: {}", created);
    }

    @DeleteMapping("/")
    public void doDelete(@RequestParam("commentId") String commentId) {
        String userId = customSecurityExpression.getUserId();
        log.info("delete comment id: {}", commentId);
       commentsService.delete(commentId,userId);
    }

}
