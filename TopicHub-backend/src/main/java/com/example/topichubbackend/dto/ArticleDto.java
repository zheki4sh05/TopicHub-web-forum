package com.example.topichubbackend.dto;

import lombok.*;

import java.sql.*;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDto {

    private Long id;
    private String theme;
    private List<String> keyWords;
    private List<ArticlePartDto> list;
    private Long likes;
    private Long dislikes;
    private Timestamp created;
    private Integer hub;
    private UserDto userDto;
    private Integer likeState;

}
