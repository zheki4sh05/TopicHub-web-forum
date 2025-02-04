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
    private Integer pageCount;
    private Integer page;
    private Long total;
}
