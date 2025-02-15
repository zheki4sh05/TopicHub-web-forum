package com.example.topichubbackend.dto;

import lombok.*;

import java.util.*;

// Чуть больше валидации можно было бы добавить как по мне,
// у тебя в dto периодически используются @NotEmpty/@NonNull/@Size,
// но также хватает dto-классов без базовой какой-нибудь валидации в целом


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleBatchDto {
    private List<ArticleDto> articleDtoList;
    public Integer getPageCount() {
        return pageCount ==0 ? 1 : pageCount;
    }
    private Integer pageCount;
    private Integer page;
    private Long total;

}
