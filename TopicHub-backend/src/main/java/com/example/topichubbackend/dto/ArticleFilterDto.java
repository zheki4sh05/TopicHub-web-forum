package com.example.topichubbackend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleFilterDto {
    private String month;
    private String year;
    private String rating;
    private Integer param;
    private Integer page;
    private Integer hub;
    private String status;
}
