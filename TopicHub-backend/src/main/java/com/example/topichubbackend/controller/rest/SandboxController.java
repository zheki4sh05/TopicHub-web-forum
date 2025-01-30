package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.config.*;
import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RequestMapping("/api/v1/sandbox")
@AllArgsConstructor
@Slf4j
@RestController
public class SandboxController{
    private final IArticleService articleService;
    private final CustomSecurityExpression customSecurityExpression;

    @PostMapping("/")
    public ResponseEntity<?> doPost(
            @RequestBody ArticleDto newArticle
    ) {
            String id = customSecurityExpression.getUserId();
            articleService.create(newArticle,id);
            return new ResponseEntity<>("", HttpStatus.CREATED);
    }
    @PutMapping("/")
    public ResponseEntity<?> doPut(
            @RequestBody ArticleDto articleDto
    ) {

            String id = customSecurityExpression.getUserId();
            articleService.update(articleDto,id);
            return new ResponseEntity<>("", HttpStatus.OK);
    }
}
