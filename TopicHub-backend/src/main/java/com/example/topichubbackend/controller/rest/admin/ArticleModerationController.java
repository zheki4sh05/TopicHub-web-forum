package com.example.topichubbackend.controller.rest.admin;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/article")
public class ArticleModerationController {

    private final IArticleService articleService;
    private final CustomSecurityExpression customSecurityExpression;

    @GetMapping("/fetch")
    public ResponseEntity<PageResponse<ArticleDto>> getModeration(
            @RequestParam Map<String, String> reqParam
    ){
        var articleFilter = HttpRequestUtils.parseFilterParams(reqParam);
        articleFilter.setUserId(customSecurityExpression.getUserId());
        var articles = articleService.fetch(articleFilter);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @PostMapping("/status")
    public  ResponseEntity<ArticleStatusDto> updateStatus(
            @Valid  @RequestBody ArticleStatusDto articleStatusDto
    ){
        articleService.update(articleStatusDto);
        return new ResponseEntity<>(articleStatusDto, HttpStatus.OK);
    }
    @DeleteMapping("/del")
    public  ResponseEntity<String> delete(
           @RequestParam("id") String id
    ){
        articleService.deleteAdmin(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}
