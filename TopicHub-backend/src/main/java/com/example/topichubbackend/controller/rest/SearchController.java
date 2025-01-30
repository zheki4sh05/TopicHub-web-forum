package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.dto.*;

import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/search")
@Slf4j
public class SearchController {

    private final IArticleService articleService;
    @GetMapping("/")
    public ResponseEntity<?> doGet(
            @RequestParam("author") String author,
            @RequestParam("theme")  String theme,
            @RequestParam("keywords")  String keywords,
            @RequestParam("user")  String user
    )  {
                ArticleBatchDto articleBatchDto = articleService.search(author,theme,keywords,user);
                return new ResponseEntity<>(articleBatchDto, HttpStatus.OK);
    }
}
