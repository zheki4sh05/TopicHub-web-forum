package com.example.topichubbackend.controller.rest;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.services.interfaces.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


/**
 * REST Controller for managing comments.
 * Provides endpoints for creating, updating, and deleting comments on articles.
 *
 * <p>This controller handles user operations related to comments, including posting new comments,
 * editing existing comments, and deleting comments. It uses the comment service to perform actions
 * on the comments and utilizes custom security expressions for user identification.
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
public class CommentsController {

    private final ICommentsService commentsService;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Creates a new comment for an article.
     *
     * <p>This endpoint allows an authenticated user to post a new comment on an article. The comment information
     * should be provided in the request body. The user ID is automatically retrieved using the custom security
     * expression.
     *
     * @param commentDto a request object containing the comment data (content, article ID, etc.).
     * @return a ResponseEntity with a 201 Created status indicating the comment was successfully created.
     * @see CommentDto
     */
    @PostMapping("/")
    public  ResponseEntity<?> doPost(@RequestBody CommentDto commentDto) {
        String userId = customSecurityExpression.getUserId();
        log.info("new comment: {}", commentDto);
        CommentDto created = commentsService.create(commentDto, userId);
        log.info("created comment: {}", created);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Updates an existing comment.
     *
     * <p>This endpoint allows an authenticated user to update an existing comment. The updated comment data should
     * be provided in the request body. The user ID is automatically retrieved using the custom security expression.
     *
     * @param commentDto a request object containing the updated comment data.
     * @return a ResponseEntity with a 200 OK status indicating the comment was successfully updated.
     * @see CommentDto
     */
    @PutMapping("/")
    public  ResponseEntity<?> doPut(@RequestBody CommentDto commentDto){
        String userId = customSecurityExpression.getUserId();
        log.info("updated comment: {}", commentDto);
        CommentDto updated = commentsService.update(commentDto, userId);
        log.info("updated comment: {}", updated);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes an existing comment.
     *
     * <p>This endpoint allows an authenticated user to delete a comment. The comment to be deleted is specified
     * by the `commentId` request parameter. The user ID is automatically retrieved using the custom security expression.
     *
     * @param commentId the ID of the comment to be deleted.
     * @return a ResponseEntity with a 200 OK status indicating the comment was successfully deleted.
     */
    @DeleteMapping("/")
    public  ResponseEntity<?> doDelete(@RequestParam("commentId") String commentId) {
        String userId = customSecurityExpression.getUserId();
        log.info("delete comment id: {}", commentId);
        commentsService.delete(commentId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
