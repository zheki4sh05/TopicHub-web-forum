package com.example.topichubbackend.dto;

import lombok.*;

import java.util.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleBatchDto {
    private List<ArticleDto> articleDtoList;
    private Integer pageCount;


}
