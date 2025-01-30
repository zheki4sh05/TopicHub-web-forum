package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RequestMapping("/api/v1/subscription")
@AllArgsConstructor
@Slf4j
@RestController
public class SubscribeServlet{

    private final IReactionService reactionService;
    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping("/")
    public ResponseEntity<?> doGet(
            @RequestParam("type") @NotNull String type
    ) {

       String id = customSecurityExpression.getUserId();
       switch(type){
           case "subscribes"->{
               List<AuthorDto> authorDtos = reactionService.fetchAllSubscribes(id);
               return new ResponseEntity<>(authorDtos, HttpStatus.OK);

           }
           case "followers"->{
               List<AuthorDto> authorDtos = reactionService.fetchAllFollowers(id);
               return new ResponseEntity<>(authorDtos, HttpStatus.OK);
           }
           default -> {
               return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
           }
       }
    }

    @PostMapping("/")
    public ResponseEntity<?> doPost(
            @RequestBody SubscriptionRequestDto subscriptionRequestDto
            ) {
            String userId = customSecurityExpression.getUserId();
            reactionService.manageSubscription(1, subscriptionRequestDto.getAuthor(),userId);
            return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> doDelete(
            @RequestParam("author") @NotNull String author
    ) {

            String userId = customSecurityExpression.getUserId();
            reactionService.manageSubscription(-1, author,userId);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
