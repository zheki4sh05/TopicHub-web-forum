package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller for managing articles and their comments.
 * Provides endpoints for fetching articles and comments related to a specific article.
 *
 */


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/article")
@Slf4j
public class ArticleController {

    private final IArticleService articleService;
    private final ICommentsService commentsService;
    /**
     * Fetches a batch of articles based on the provided filter parameters.
     *
     * <p>This endpoint returns a batch of articles with the status "PUBLISHED". The filter
     * parameters should be provided as request parameters. For example, the request URL could be:
     * <pre>
     * GET /api/v1/article?status=PUBLISHED&category=technology&page=1
     * </pre>
     * The available filter parameters are parsed and applied to fetch the corresponding articles.
     *
     * @param reqParam a map containing the filter parameters (e.g., status, category, page, etc.).
     *                 The status will be set to "PUBLISHED" by default.
     * @return a ResponseEntity containing a batch of articles matching the provided filter parameters.
     * @see ArticleBatchDto
     * @see ArticleFilterDto
     */

    @GetMapping("")
    public ResponseEntity<?> doGet(
            @RequestParam Map<String, String> reqParam

    )  {
                reqParam.put("status", StatusDto.PUBLISH.type());
                ArticleFilterDto articleFilterDto = HttpRequestUtils.parseFilterParams(reqParam);
                ArticleBatchDto articleBatchDto;
                articleBatchDto = articleService.fetch(articleFilterDto);
                return new ResponseEntity<>(articleBatchDto, HttpStatus.OK);
    }

    /**
     * Fetches a list of comments related to a specific article.
     *
     * <p>This endpoint returns the comments for the article specified by the article ID. The request URL
     * might look like:
     * <pre>
     * GET /api/v1/article/answers?articleId=12345
     * </pre>
     * The articleId parameter is mandatory and must be provided.
     *
     * @param articleId the unique identifier of the article for which comments are to be fetched.
     * @return a ResponseEntity containing the list of comments related to the article.
     * @see CommentDto
     */
    @GetMapping("/answers")
    public ResponseEntity<?> doGet(
            @RequestParam("articleId") @NotNull String articleId
    ) {
            List<CommentDto> commentDtoList = commentsService.fetch(articleId);
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

}