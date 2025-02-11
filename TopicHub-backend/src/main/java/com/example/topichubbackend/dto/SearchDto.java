package com.example.topichubbackend.dto;

import lombok.*;

@Builder
@Getter
@Setter
public class SearchDto{
    private String theme;
    private String keywords;
    private String author;
    private String userId;
    private ArticleFilterDto articleFilterDto;

    public SearchDto(String theme, String keywords, String author, String userId, ArticleFilterDto articleFilterDto) {
        this.theme = theme;
        this.keywords = keywords;
        this.author = author;
        this.userId = userId;
        this.articleFilterDto = articleFilterDto;
    }

    public SearchDto() {
    }

    public String getUserId() {
        return userId;
    }
}
