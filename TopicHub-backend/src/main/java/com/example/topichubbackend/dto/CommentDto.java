package com.example.topichubbackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private Integer articleId;
    private Integer commentId;
    private String value;
}
