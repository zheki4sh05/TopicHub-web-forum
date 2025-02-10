package com.example.topichubbackend.dto;

import lombok.*;

@Builder
@Getter
public class SearchDto{
    private String theme;
    private String keywords;
    private String author;
    private String userId;
    private ArticleFilterDto articleFilterDto;

    public String getUserId() {
        return userId;
    }
}
