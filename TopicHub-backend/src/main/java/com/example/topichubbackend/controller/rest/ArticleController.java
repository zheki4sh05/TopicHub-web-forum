package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.services.interfaces.*;
import com.example.topichubbackend.util.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/article")
@Slf4j
public class ArticleController {

    private final IArticleService articleService;
    private final ICommentsService commentsService;

    @GetMapping("")
    public ResponseEntity<?> doGet(
            @RequestParam Map<String, String> reqParam)  {
            if(!reqParam.get("status").equals(StatusDto.PUBLISH.type())){
                throw new BadRequestException();
            }else{
                ArticleFilterDto articleFilterDto = HttpRequestUtils.parseFilterParams(reqParam);
                ArticleBatchDto articleBatchDto;
                articleBatchDto = articleService.fetch(articleFilterDto);
                return new ResponseEntity<>(articleBatchDto, HttpStatus.OK);
            }

//            if(type.equals("author")){
//                String otherUserId = reqParam.get("otherUserId");
//                log.info("filter dto: {}",articleFilterDto);
//                articleBatchDto = articleService.fetch(articleFilterDto,userId,otherUserId);
//            }else{
//                log.info("filter dto: {}",articleFilterDto);
//                articleFilterDto.setParam(Integer.valueOf(reqParam.get("hub")));
//                 articleBatchDto = articleService.fetch(articleFilterDto,userId);
//            }


    }



    @GetMapping("/answers")
    public ResponseEntity<?> doGet(
            @RequestParam("articleId") @NotNull String articleId
    ) {
            List<CommentDto> commentDtoList = commentsService.fetch(articleId);
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }


    private ArticleFilterDto parseFilterParams(Map<String, String> reqParam) {
        return ArticleFilterDto.builder()
                .month(reqParam.get("month"))
                .year(reqParam.get("year"))
                .rating(reqParam.get("rating"))
                .page(Integer.valueOf(reqParam.get("page")))
                .hub(Integer.valueOf(reqParam.get("hub")))
                .build();
    }

}