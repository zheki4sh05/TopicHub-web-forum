package com.example.topichubbackend.controller.rest;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing user bookmarks.
 * Provides endpoints for fetching, adding, and removing bookmarks for articles.
 *
 * <p>This controller allows users to fetch their bookmarked articles, add articles to their bookmarks,
 * and remove articles from their bookmarks. It integrates with the reaction service to manage bookmark actions
 * and the article service to fetch bookmarked articles.
 *
 */
@RestController
@RequestMapping("api/v1/bookmarks")
@AllArgsConstructor
public class BookmarksController {

    private final IReactionService reactionService;
    private final IArticleService articleService;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Fetches a page of bookmarked articles for the authenticated user.
     *
     * <p>This endpoint retrieves a list of articles that the authenticated user has bookmarked.
     * The results are paginated, with the page number provided as a request parameter.
     *
     * @param page the page number to retrieve, for pagination.
     * @return a ResponseEntity containing a batch of bookmarked articles for the user.
     *         A 200 OK status is returned along with the list of articles.
     * @see ArticleBatchDto
     */
    @GetMapping("/")
    public ResponseEntity<?> fetchPage(
            @RequestParam Integer page) {
        var userId = customSecurityExpression.getUserId();
        ArticleBatchDto articleBatchDto = articleService.fetchBookMarks(userId, page);
        return new ResponseEntity<>(articleBatchDto, HttpStatus.OK);
    }

    /**
     * Adds an article to the authenticated user's bookmarks.
     *
     * <p>This endpoint allows the user to add an article to their bookmarks.
     * The request body should include the article information (e.g., article ID).
     *
     * @param bookmarksRequestDto a request object containing the article to be added to the bookmarks.
     * @return a ResponseEntity with a 201 Created status if the article was successfully added to the bookmarks.
     * @see BookmarksRequestDto
     */
    @PostMapping("/")
    public ResponseEntity<?> create(
            @RequestBody BookmarksRequestDto bookmarksRequestDto
    ){
        String userId = customSecurityExpression.getUserId();
        reactionService.manageBookmarks(1, bookmarksRequestDto.getArticle(), userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Removes an article from the authenticated user's bookmarks.
     *
     * <p>This endpoint allows the user to remove an article from their bookmarks.
     * The article ID should be provided as a request parameter.
     *
     * @param article the ID of the article to be removed from the bookmarks.
     * @return a ResponseEntity with a 200 OK status if the article was successfully removed from the bookmarks.
     */
    @DeleteMapping("/")
    public ResponseEntity<?> doDelete(@RequestParam("article") @NotNull String article){
        String userId = customSecurityExpression.getUserId();
        reactionService.manageBookmarks(-1, article, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

