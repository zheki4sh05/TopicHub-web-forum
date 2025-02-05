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
    public Integer getPageCount() {
        return pageCount ==0 ? 1 : pageCount;
    }
    private Integer pageCount;
    private Integer page;
    private Long total;
}
