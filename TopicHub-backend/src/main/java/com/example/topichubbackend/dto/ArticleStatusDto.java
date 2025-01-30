package com.example.topichubbackend.dto;

import lombok.*;

@AllArgsConstructor
@Builder
public class ArticleStatusDto {
    private String id;
    private StatusDto status;
}
