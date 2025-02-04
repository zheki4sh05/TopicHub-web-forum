package com.example.topichubbackend.dto;

import lombok.*;

import java.util.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ArticleBatchDto {
    private List<ArticleDto> articleDtoList;
    private Long pageCount;
    private Integer page;
}
