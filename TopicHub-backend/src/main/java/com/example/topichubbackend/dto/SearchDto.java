package com.example.topichubbackend.dto;

import lombok.*;

@Builder
@Getter
public class SearchDto{
    private String theme;
    private String keywords;
    private String author;
    private ArticleFilterDto articleFilterDto;
}
