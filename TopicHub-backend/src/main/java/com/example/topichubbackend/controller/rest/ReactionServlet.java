package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reaction")
public class ReactionServlet{
    private final IReactionService reactionService;
    private final CustomSecurityExpression customSecurityExpression;

    @PostMapping("/")
    public ResponseEntity<?> doPost(
            @RequestBody  LikeRequestDto reactionDto
    ) {

        String userId = customSecurityExpression.getUserId();
        reactionService.makeReaction(reactionDto.getType(), Integer.valueOf(reactionDto.getValue()), userId,Long.valueOf(reactionDto.getTargetId()));
        return new ResponseEntity<>(reactionDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> doDelete(
            @RequestParam("type") @NotNull String type,
            @RequestParam("targetId") @NotNull  String targetId
    )  {
        String userId = customSecurityExpression.getUserId();
        reactionService.removeReaction(type,userId,Long.valueOf(targetId));
        return new ResponseEntity<>("", HttpStatus.OK);
        }



    @GetMapping("/")
    public ResponseEntity<?> doGet(
            @RequestParam("articleId") @NotNull String articleId,
            @RequestParam("authorId") @NotNull String authorId
    ) {

        String userId = customSecurityExpression.getUserId();
            ReactionDto reactionDto = reactionService.check(articleId,authorId, userId);
        return new ResponseEntity<>(reactionDto, HttpStatus.OK);
        }
}
