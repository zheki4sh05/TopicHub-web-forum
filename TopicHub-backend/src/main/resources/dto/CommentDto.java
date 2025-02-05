package com.example.topichubbackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDto {

    private String id;

    @NonNull
    private Long articleId;

    private UserDto userDto;

    private AuthorDto authorDto;

    private String parentId;
    private List<CommentDto> replies;

    private Timestamp created;

    @NotEmpty
    @NonNull
    private String value;
}
