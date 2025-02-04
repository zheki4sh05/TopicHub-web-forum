package com.example.topichubbackend.mapper;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface CommentMapper {

    @Mapping(target = "id", expression = "java(comment.getId().toString())")
    @Mapping(target = "value", source = "message")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "articleId", expression = "java(comment.getArticle().getId())")
    @Mapping(target = "authorDto", source = "author")
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "userDto", ignore = true)
    @Mapping(target = "parentId", ignore = true)
    CommentDto mapFrom(Comment comment);

}
