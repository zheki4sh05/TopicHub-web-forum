package com.example.topichubbackend.dto;

import lombok.*;

import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDto {
    private String theme;
    private List<String> keyWords;
    private List<ArticlePartDto> list;
    private Integer likes;
    private Integer dislikes;

}
