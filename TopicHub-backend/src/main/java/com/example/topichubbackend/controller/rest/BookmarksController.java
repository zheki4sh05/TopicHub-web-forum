package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;


@RestController
@RequestMapping("api/v1/bookmarks")
@AllArgsConstructor
public class BookmarksController{
    private final IReactionService reactionService;
    private final IArticleService articleService;
    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping("/")
    public ResponseEntity<?> fetchPage(
                @RequestParam Integer page) {
                var userId = customSecurityExpression.getUserId();
                 ArticleBatchDto articleBatchDto = articleService.fetchBookMarks(userId, page);
                return new ResponseEntity<>(articleBatchDto, HttpStatus.OK);
        }

    @PostMapping("/")
    public ResponseEntity<?> create(
            @RequestBody BookmarksRequestDto bookmarksRequestDto
    ){
            String userId = customSecurityExpression.getUserId();
            reactionService.manageBookmarks(1, bookmarksRequestDto.getArticle(),userId);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public void doDelete(@RequestParam("article") @NotNull String article){
            String userId = customSecurityExpression.getUserId();
            reactionService.manageBookmarks(-1, article,userId);
    }
}
